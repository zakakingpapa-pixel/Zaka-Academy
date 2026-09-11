package com.example.ai.provider

import com.example.ai.rag.RetrievedKnowledge
import com.example.ai.router.RoutedQuery
import com.example.model.ChatMessage
import com.example.model.QueryIntent
import com.example.model.TeachingStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

sealed class ProviderResult {
    data class Success(val answer: String, val providerName: String) : ProviderResult()
    data class Error(val errorMessage: String, val technicalDiagnostic: String) : ProviderResult()
}

interface AiProvider {
    val providerName: String
    suspend fun generateResponse(
        routedQuery: RoutedQuery,
        knowledge: RetrievedKnowledge,
        style: TeachingStyle,
        history: List<ChatMessage>
    ): ProviderResult
}

/**
 * High-performance Offline Expert Provider.
 * Delivers verified, textbook-grade Computer Science answers instantly with zero network requirement.
 */
class OfflineExpertProvider : AiProvider {
    override val providerName: String = "ZAKA Offline Expert"

    override suspend fun generateResponse(
        routedQuery: RoutedQuery,
        knowledge: RetrievedKnowledge,
        style: TeachingStyle,
        history: List<ChatMessage>
    ): ProviderResult = withContext(Dispatchers.Default) {
        val concept = routedQuery.matchedFastConcept
        val topic = knowledge.matchedTopic
        val isRomanUrdu = routedQuery.detectedLanguage == "Roman Urdu" || routedQuery.detectedLanguage == "Urdu"

        val sb = StringBuilder()

        // 1. Concept / Topic Resolution
        if (concept != null) {
            if (isRomanUrdu) {
                sb.appendLine("💡 **${concept.name}**\n")
                sb.appendLine("**Asan Wazahat (Simple Explanation):**")
                sb.appendLine(concept.simpleExplanation)
                sb.appendLine("\n**Tareef (Definition):**")
                sb.appendLine(concept.definition)

                if (style == TeachingStyle.BALANCED || style == TeachingStyle.ADVANCED) {
                    sb.appendLine("\n**Tafseel (Detailed Architecture):**")
                    sb.appendLine(concept.detailedExplanation)
                }

                sb.appendLine("\n**Misaal (Real Example):**")
                sb.appendLine(concept.examples)

                sb.appendLine("\n**Ahem Nikaat (Important Points):**")
                concept.importantPoints.forEach { point ->
                    sb.appendLine("• $point")
                }

                if (concept.commonConfusion.isNotBlank()) {
                    sb.appendLine("\n⚠️ **Aam Ghalat Fehmi (Common Confusion):**")
                    sb.appendLine(concept.commonConfusion)
                }

                if (knowledge.examPoints.isNotEmpty()) {
                    sb.appendLine("\n📝 **Exam Points (Class 9 Focus):**")
                    knowledge.examPoints.take(3).forEach { ep ->
                        sb.appendLine("✓ $ep")
                    }
                }
            } else {
                sb.appendLine("💡 **${concept.name}**\n")
                sb.appendLine("**Definition:**")
                sb.appendLine(concept.definition)

                sb.appendLine("\n**Simple Explanation & Analogy:**")
                sb.appendLine(concept.simpleExplanation)

                if (style == TeachingStyle.BALANCED || style == TeachingStyle.ADVANCED) {
                    sb.appendLine("\n**Technical Architecture & Mechanism:**")
                    sb.appendLine(concept.detailedExplanation)
                }

                sb.appendLine("\n**Real-World Example:**")
                sb.appendLine(concept.examples)

                sb.appendLine("\n**Key Takeaways:**")
                concept.importantPoints.forEach { point ->
                    sb.appendLine("• $point")
                }

                if (concept.commonConfusion.isNotBlank()) {
                    sb.appendLine("\n⚠️ **Common Misconception:**")
                    sb.appendLine(concept.commonConfusion)
                }

                if (concept.relatedConcepts.isNotEmpty()) {
                    sb.appendLine("\n🔗 **Related Concepts:** ${concept.relatedConcepts.joinToString(", ")}")
                }

                if (knowledge.examPoints.isNotEmpty()) {
                    sb.appendLine("\n📝 **Board Exam Pointers:**")
                    knowledge.examPoints.take(3).forEach { ep ->
                        sb.appendLine("✓ $ep")
                    }
                }
            }
        } else if (topic != null) {
            // Course Topic Fallback
            sb.appendLine("📚 **Class 9 Course: ${topic.title}** (${topic.chapterTitle})\n")
            sb.appendLine("**Definition:**")
            sb.appendLine(topic.definition)
            sb.appendLine("\n**Easy Explanation:**")
            sb.appendLine(topic.easyExplanation)
            sb.appendLine("\n**Real-World Example:**")
            sb.appendLine(topic.realWorldExample)

            sb.appendLine("\n**Key Points:**")
            topic.importantPoints.take(4).forEach { p -> sb.appendLine("• $p") }

            if (topic.shortQuestions.isNotEmpty()) {
                sb.appendLine("\n❓ **Practice Exam Question:**")
                val sq = topic.shortQuestions.first()
                sb.appendLine("**Q:** ${sq.question}")
                sb.appendLine("**A:** ${sq.answer}")
            }
        } else {
            // Heuristic Synthesis for General CS queries
            if (isRomanUrdu) {
                sb.appendLine("🤖 **ZAKA AI Teacher:**")
                sb.appendLine("Aapka sawal Computer Science ke baray mein hai: \"${routedQuery.rawQuery}\".")
                sb.appendLine("\nComputer Science mein, tamam digital systems hardware (CPU, Memory, Storage) aur software (Algorithms, Operating Systems) ke zariye kaam kartay hain.")
                sb.appendLine("Aap specific topic (jaise RAM, ROM, CPU, Algorithm, Python, Binary) ke baray mein poochein taake mukammal textbook notes aur misalein faraham ki ja sakein.")
            } else {
                sb.appendLine("🤖 **ZAKA AI Teacher:**")
                sb.appendLine("Regarding your question: \"${routedQuery.rawQuery}\":")
                sb.appendLine("\nIn Computer Science, digital systems execute operations through foundational components: Processing (CPU), Memory (RAM/Cache), Logic (Algorithms & Data Structures), and Storage.")
                sb.appendLine("Tip: You can query specific topics like 'What is RAM?', 'Algorithm flowchart', 'Binary conversion', or 'Python loops' for instant full textbook lessons!")
            }
        }

        ProviderResult.Success(sb.toString().trim(), providerName)
    }
}

/**
 * Cloud Gemini AI Provider with strict 10s timeout and safe fallback triggers.
 */
class GeminiProvider(private val apiKey: String) : AiProvider {
    override val providerName: String = "Google Gemini AI"

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    override suspend fun generateResponse(
        routedQuery: RoutedQuery,
        knowledge: RetrievedKnowledge,
        style: TeachingStyle,
        history: List<ChatMessage>
    ): ProviderResult = withContext(Dispatchers.IO) {
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext ProviderResult.Error(
                errorMessage = "Gemini API key is not configured.",
                technicalDiagnostic = "Missing GEMINI_API_KEY in app settings or environment."
            )
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

            val systemInstruction = "You are ZAKA AI, the master Computer Science teacher for ZAKA ACADEMY. " +
                    "Teach student friendly, technically rigorous CS concepts. " +
                    "Respond in the user's language: if they ask in Roman Urdu (e.g. 'kya hai', 'samjhao'), respond in fluent friendly Roman Urdu. " +
                    "If they ask in English, respond in English. " +
                    "Teaching style: ${style.name}. " +
                    (if (knowledge.courseExcerpt.isNotBlank()) "Prioritize this Class 9 Course Material:\n${knowledge.courseExcerpt}" else "")

            val contentsArray = JSONArray()

            // Add recent history (up to 4 turns)
            history.takeLast(4).forEach { msg ->
                val role = if (msg.sender == com.example.model.MessageSender.USER) "user" else "model"
                val partObj = JSONObject().put("text", msg.text)
                val contentObj = JSONObject()
                    .put("role", role)
                    .put("parts", JSONArray().put(partObj))
                contentsArray.put(contentObj)
            }

            // Current prompt
            val currentTurn = JSONObject()
                .put("role", "user")
                .put("parts", JSONArray().put(JSONObject().put("text", routedQuery.rawQuery)))
            contentsArray.put(currentTurn)

            val rootJson = JSONObject().apply {
                put("contents", contentsArray)
                put("systemInstruction", JSONObject().put("parts", JSONArray().put(JSONObject().put("text", systemInstruction))))
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("maxOutputTokens", 1200)
                })
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = rootJson.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext ProviderResult.Error(
                    errorMessage = "Gemini API error (HTTP ${response.code})",
                    technicalDiagnostic = "Response: $responseBody"
                )
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.getJSONObject("content")
                val parts = content.getJSONArray("parts")
                val text = parts.getJSONObject(0).getString("text")
                return@withContext ProviderResult.Success(text.trim(), providerName)
            } else {
                return@withContext ProviderResult.Error(
                    errorMessage = "Empty response from Gemini API.",
                    technicalDiagnostic = "Candidates array was empty or blocked."
                )
            }
        } catch (e: Exception) {
            return@withContext ProviderResult.Error(
                errorMessage = "Network or timeout error contacting Gemini AI.",
                technicalDiagnostic = e.localizedMessage ?: e.javaClass.simpleName
            )
        }
    }
}

/**
 * Local Ollama / LAN AI Provider for running local models on a local machine (e.g. Qwen2.5-Coder, Llama).
 */
class OllamaLanProvider(
    private val endpointUrl: String,
    private val modelName: String
) : AiProvider {
    override val providerName: String = "Ollama Local ($modelName)"

    private val client = OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)
        .readTimeout(12, TimeUnit.SECONDS)
        .writeTimeout(8, TimeUnit.SECONDS)
        .build()

    override suspend fun generateResponse(
        routedQuery: RoutedQuery,
        knowledge: RetrievedKnowledge,
        style: TeachingStyle,
        history: List<ChatMessage>
    ): ProviderResult = withContext(Dispatchers.IO) {
        val cleanUrl = if (endpointUrl.endsWith("/")) "${endpointUrl}api/generate" else "$endpointUrl/api/generate"

        try {
            val prompt = "You are ZAKA AI, an expert Computer Science teacher. Teach clearly. " +
                    "Query: ${routedQuery.rawQuery}\n" +
                    (if (knowledge.courseExcerpt.isNotBlank()) "Course context:\n${knowledge.courseExcerpt}\n" else "") +
                    "Answer in the student's language (Roman Urdu or English):"

            val json = JSONObject().apply {
                put("model", modelName)
                put("prompt", prompt)
                put("stream", false)
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = json.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url(cleanUrl)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext ProviderResult.Error(
                    errorMessage = "Ollama server returned HTTP ${response.code}",
                    technicalDiagnostic = responseBody
                )
            }

            val jsonObj = JSONObject(responseBody)
            val text = jsonObj.optString("response", "")
            if (text.isNotBlank()) {
                ProviderResult.Success(text.trim(), providerName)
            } else {
                ProviderResult.Error(
                    errorMessage = "Ollama returned empty response.",
                    technicalDiagnostic = responseBody
                )
            }
        } catch (e: Exception) {
            ProviderResult.Error(
                errorMessage = "Cannot connect to local Ollama server at $endpointUrl.",
                technicalDiagnostic = "Check LAN IP, port 11434, and network permissions: ${e.message}"
            )
        }
    }
}
