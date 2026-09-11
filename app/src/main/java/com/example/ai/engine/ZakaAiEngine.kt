package com.example.ai.engine

import com.example.ai.provider.*
import com.example.ai.rag.KnowledgeRetriever
import com.example.ai.rag.RetrievedKnowledge
import com.example.ai.router.QueryRouter
import com.example.ai.router.RoutedQuery
import com.example.feature.tester.AnswerEvaluator
import com.example.model.*
import kotlinx.coroutines.withTimeoutOrNull

class ZakaAiEngine(
    private var settings: AppSettings
) {
    private val offlineProvider = OfflineExpertProvider()
    private var lastTopicSubject: String? = null

    fun updateSettings(newSettings: AppSettings) {
        this.settings = newSettings
    }

    /**
     * Grades a written answer. When a cloud/LAN provider is configured the model is asked for a
     * structured verdict; if it is unavailable, unparsable or times out, the deterministic offline
     * rubric grades instead and the result says so.
     */
    suspend fun evaluateWrittenAnswer(
        question: PracticeQuestion,
        studentAnswer: String
    ): AnswerEvaluation {
        val offline = AnswerEvaluator.evaluate(question, studentAnswer)
        if (studentAnswer.isBlank() || settings.providerType == AiProviderType.OFFLINE_EXPERT) return offline

        val provider = selectProvider() ?: return offline
        val prompt = buildGradingPrompt(question, studentAnswer)
        val routed = RoutedQuery(
            rawQuery = prompt,
            normalizedQuery = prompt,
            correctedQuery = prompt,
            detectedLanguage = "English",
            intent = QueryIntent.COURSE_EXAM_QUESTION,
            matchedFastConcept = null,
            isFollowUp = false
        )
        val knowledge = RetrievedKnowledge(
            matchedTopic = null,
            relatedTopics = emptyList(),
            courseExcerpt = "Topic: ${question.topicTitle} (${question.chapterTitle})\nModel answer: ${question.modelAnswer}",
            examPoints = question.keyPoints,
            shortQuestions = emptyList()
        )

        val result = try {
            withTimeoutOrNull(15000L) {
                provider.generateResponse(routed, knowledge, settings.teachingStyle, emptyList())
            }
        } catch (e: Exception) {
            null
        }

        val success = result as? ProviderResult.Success ?: return offline
        return parseGradingResponse(success.answer, question, success.providerName) ?: offline
    }

    private fun buildGradingPrompt(question: PracticeQuestion, studentAnswer: String): String = """
        You are grading a Class 9 Computer Science practice answer. Judge conceptual correctness,
        coverage of the important points, relevance and factual accuracy. Never grade on exact wording.

        QUESTION: ${question.question}
        MODEL ANSWER: ${question.modelAnswer}
        EXPECTED POINTS: ${question.keyPoints.joinToString(" | ")}
        STUDENT ANSWER: $studentAnswer

        Reply using exactly these lines and nothing else:
        SCORE: <integer 0-100>
        VERDICT: <CORRECT|PARTIAL|INCORRECT>
        RIGHT: <what the student got right, one line>
        MISSING: <what is missing or wrong, one line>
        FEEDBACK: <one short correction, one line>
        IMPROVED: <a board-ready model answer, one line>
    """.trimIndent()

    private fun parseGradingResponse(
        raw: String,
        question: PracticeQuestion,
        providerName: String
    ): AnswerEvaluation? {
        fun field(name: String): String? = Regex("^\\s*$name\\s*:\\s*(.+)$", RegexOption.MULTILINE)
            .find(raw)?.groupValues?.get(1)?.trim()?.takeIf { it.isNotEmpty() }

        val score = field("SCORE")?.filter { it.isDigit() }?.toIntOrNull()?.coerceIn(0, 100) ?: return null
        val verdict = when (field("VERDICT")?.uppercase()) {
            "CORRECT" -> AnswerVerdict.CORRECT
            "PARTIAL", "PARTIALLY CORRECT" -> AnswerVerdict.PARTIALLY_CORRECT
            "INCORRECT" -> AnswerVerdict.INCORRECT
            else -> when {
                score >= 75 -> AnswerVerdict.CORRECT
                score >= 40 -> AnswerVerdict.PARTIALLY_CORRECT
                else -> AnswerVerdict.INCORRECT
            }
        }

        return AnswerEvaluation(
            verdict = verdict,
            scorePercent = score,
            coveredPoints = listOfNotNull(field("RIGHT")),
            missingPoints = listOfNotNull(field("MISSING")),
            feedback = field("FEEDBACK") ?: field("RIGHT").orEmpty(),
            modelAnswer = field("IMPROVED") ?: question.modelAnswer,
            evaluatedBy = providerName,
            isAiEvaluation = true
        )
    }

    private fun selectProvider(): AiProvider? = when (settings.providerType) {
        AiProviderType.OFFLINE_EXPERT -> null
        AiProviderType.OPENAI_COMPATIBLE -> null
        AiProviderType.GEMINI_CLOUD -> {
            val key = if (settings.geminiApiKey.isNotBlank()) settings.geminiApiKey else try {
                com.example.BuildConfig.GEMINI_API_KEY
            } catch (e: Throwable) { "" }
            if (key.isBlank()) null else GeminiProvider(key)
        }
        AiProviderType.OLLAMA_LAN -> OllamaLanProvider(settings.ollamaEndpoint, settings.ollamaModel)
    }

    /**
     * Executes the 13-stage AI response pipeline with strict timeout and fallback guarantees.
     * Guaranteed to never hang or return an infinite loading state.
     */
    suspend fun processQuery(
        rawQuery: String,
        history: List<ChatMessage>
    ): ChatMessage {
        // 1. Route query, normalize, detect language, typo correct, classify intent
        val routed = QueryRouter.route(rawQuery, lastTopicSubject)

        // Track subject for follow-up questions
        if (routed.matchedFastConcept != null) {
            lastTopicSubject = routed.matchedFastConcept.name
        }

        // 2. RAG Knowledge Retrieval
        val knowledge = KnowledgeRetriever.retrieve(routed)

        // 3. Select AI Provider
        val provider: AiProvider = when (settings.providerType) {
            AiProviderType.OFFLINE_EXPERT -> offlineProvider
            AiProviderType.GEMINI_CLOUD -> {
                val key = if (settings.geminiApiKey.isNotBlank()) settings.geminiApiKey else try {
                    com.example.BuildConfig.GEMINI_API_KEY
                } catch (e: Throwable) { "" }
                GeminiProvider(key)
            }
            AiProviderType.OLLAMA_LAN -> OllamaLanProvider(
                endpointUrl = settings.ollamaEndpoint,
                modelName = settings.ollamaModel
            )
            AiProviderType.OPENAI_COMPATIBLE -> offlineProvider // Fallback
        }

        // 4. Execute with strict 10-second timeout to prevent ANY hanging
        val result: ProviderResult? = try {
            withTimeoutOrNull(10000L) {
                provider.generateResponse(
                    routedQuery = routed,
                    knowledge = knowledge,
                    style = settings.teachingStyle,
                    history = history
                )
            }
        } catch (e: Exception) {
            ProviderResult.Error(
                errorMessage = "Execution error: ${e.message}",
                technicalDiagnostic = e.stackTraceToString()
            )
        }

        // 5. Response handling and graceful fallback
        return when (result) {
            is ProviderResult.Success -> {
                ChatMessage(
                    sender = MessageSender.AI,
                    text = result.answer,
                    providerUsed = result.providerName,
                    isFallback = false,
                    detectedLanguage = routed.detectedLanguage,
                    intent = routed.intent
                )
            }
            is ProviderResult.Error, null -> {
                // If cloud or local LAN failed, immediately fall back to Offline Expert
                val fallbackResult = offlineProvider.generateResponse(
                    routedQuery = routed,
                    knowledge = knowledge,
                    style = settings.teachingStyle,
                    history = history
                )

                val fallbackText = (fallbackResult as? ProviderResult.Success)?.answer ?: "Here is the Computer Science explanation for your question."

                val notice = if (routed.detectedLanguage == "Roman Urdu") {
                    "⚠️ *Notice: Cloud/Local AI service dastiyab nahi hai, is liye ZAKA AI ka verified Offline Expert mode use ho raha hai.*\n\n"
                } else {
                    "⚠️ *Notice: Configured AI service was unreachable or timed out; ZAKA AI Offline Expert mode has answered instead.*\n\n"
                }

                ChatMessage(
                    sender = MessageSender.AI,
                    text = notice + fallbackText,
                    providerUsed = "Offline Expert (Fallback)",
                    isFallback = true,
                    detectedLanguage = routed.detectedLanguage,
                    intent = routed.intent
                )
            }
        }
    }
}
