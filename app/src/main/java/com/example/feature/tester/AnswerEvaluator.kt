package com.example.feature.tester

import com.example.model.AnswerEvaluation
import com.example.model.AnswerVerdict
import com.example.model.PracticeQuestion

/**
 * Offline rubric for written answers.
 *
 * Grading is concept coverage based rather than exact text matching: the model answer is reduced to
 * its significant terms, and the student's answer is scored on how many of those terms and key
 * points it actually covers. It always works without a network or AI provider.
 */
object AnswerEvaluator {

    private val stopWords = setOf(
        "the", "a", "an", "and", "or", "of", "to", "in", "is", "are", "was", "were", "it", "its",
        "that", "this", "these", "those", "for", "on", "with", "as", "by", "be", "been", "from",
        "which", "into", "at", "can", "will", "not", "but", "than", "then", "when", "while",
        "they", "them", "their", "there", "also", "such", "each", "any", "all", "has", "have",
        "had", "may", "must", "should", "would", "could", "e.g", "i.e", "etc", "example"
    )

    fun evaluate(question: PracticeQuestion, studentAnswer: String): AnswerEvaluation {
        val answer = studentAnswer.trim()
        if (answer.isEmpty()) {
            return AnswerEvaluation(
                verdict = AnswerVerdict.NOT_ATTEMPTED,
                scorePercent = 0,
                coveredPoints = emptyList(),
                missingPoints = question.keyPoints,
                feedback = "No answer was written, so nothing could be evaluated.",
                modelAnswer = question.modelAnswer,
                evaluatedBy = "ZAKA Offline Rubric",
                isAiEvaluation = false
            )
        }

        val expectedTerms = significantTerms(question.modelAnswer)
        val studentTerms = significantTerms(answer)
        val matchedTerms = expectedTerms.filter { term -> studentTerms.any { it.matches(term) } }

        val termScore = if (expectedTerms.isEmpty()) 0f else matchedTerms.size.toFloat() / expectedTerms.size
        val covered = question.keyPoints.filter { point -> pointIsCovered(point, studentTerms) }
        val missing = question.keyPoints - covered.toSet()
        val pointScore = if (question.keyPoints.isEmpty()) termScore else covered.size.toFloat() / question.keyPoints.size

        val lengthRatio = (answer.length.toFloat() / question.modelAnswer.length.coerceAtLeast(1)).coerceAtMost(1f)
        val rawScore = (0.55f * termScore + 0.35f * pointScore + 0.10f * lengthRatio) * 100f
        val score = rawScore.toInt().coerceIn(0, 100)

        val verdict = when {
            score >= 75 -> AnswerVerdict.CORRECT
            score >= 40 -> AnswerVerdict.PARTIALLY_CORRECT
            else -> AnswerVerdict.INCORRECT
        }

        val feedback = buildString {
            append(
                when (verdict) {
                    AnswerVerdict.CORRECT -> "Your answer covers the concept well."
                    AnswerVerdict.PARTIALLY_CORRECT -> "You have the right idea, but the answer is incomplete."
                    else -> "The answer misses the core concept being asked."
                }
            )
            if (matchedTerms.isNotEmpty()) {
                append(" Key terms you used correctly: ")
                append(matchedTerms.take(6).joinToString(", "))
                append('.')
            }
            val missedTerms = (expectedTerms - matchedTerms.toSet()).take(6)
            if (missedTerms.isNotEmpty()) {
                append(" Terms the board answer expects: ")
                append(missedTerms.joinToString(", "))
                append('.')
            }
        }

        return AnswerEvaluation(
            verdict = verdict,
            scorePercent = score,
            coveredPoints = covered,
            missingPoints = missing,
            feedback = feedback,
            modelAnswer = question.modelAnswer,
            evaluatedBy = "ZAKA Offline Rubric",
            isAiEvaluation = false
        )
    }

    /**
     * Progressive hints derived from the model answer: a nudge, then guidance, then the core concept.
     */
    fun hints(question: PracticeQuestion): List<String> {
        val terms = significantTerms(question.modelAnswer)
        val firstSentence = question.modelAnswer
            .split(". ")
            .firstOrNull()
            ?.trim()
            .orEmpty()

        val hint1 = "Think about the topic \"${question.topicTitle}\" from ${question.chapterTitle}."
        val hint2 = if (terms.isEmpty()) {
            "Structure your answer as: definition, then one example."
        } else {
            "Your answer should mention: ${terms.take(4).joinToString(", ")}."
        }
        val hint3 = question.keyPoints.firstOrNull()?.let { "Key concept: $it" }
            ?: if (firstSentence.isNotEmpty()) "Key concept: $firstSentence." else "Key concept: revise the definition in the notes."

        return listOf(hint1, hint2, hint3)
    }

    internal fun significantTerms(text: String): List<String> =
        text.lowercase()
            .split(Regex("[^a-z0-9+#]+"))
            .filter { it.length > 3 && it !in stopWords }
            .distinct()
            .sortedByDescending { it.length }
            .take(14)

    private fun String.matches(term: String): Boolean =
        this == term || (length > 4 && term.length > 4 && (startsWith(term.take(5)) || term.startsWith(take(5))))

    private fun pointIsCovered(point: String, studentTerms: List<String>): Boolean {
        val pointTerms = significantTerms(point)
        if (pointTerms.isEmpty()) return false
        val hits = pointTerms.count { term -> studentTerms.any { it.matches(term) } }
        return hits.toFloat() / pointTerms.size >= 0.4f
    }
}
