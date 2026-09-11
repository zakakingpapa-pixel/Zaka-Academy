package com.example.ai.router

import com.example.data.fastconcept.FastConceptData
import com.example.model.FastConcept
import com.example.model.QueryIntent

data class RoutedQuery(
    val rawQuery: String,
    val normalizedQuery: String,
    val correctedQuery: String,
    val detectedLanguage: String, // "English", "Roman Urdu", "Urdu"
    val intent: QueryIntent,
    val matchedFastConcept: FastConcept?,
    val isFollowUp: Boolean,
    val comparisonSubjects: Pair<String, String>? = null
)

object QueryRouter {

    private val romanUrduKeywords = setOf(
        "kya", "kia", "hota", "hoti", "hote", "ha", "hai", "hain", "ka", "ki", "ke",
        "kam", "kaam", "samjhao", "batao", "bataen", "asan", "lafzon", "ma", "mein",
        "kaise", "kaisay", "kyun", "wala", "wali", "wale", "kuch", "or", "aur", "ye",
        "yeh", "wo", "woh", "sir", "bhai", "shukriya", "plz", "code", "likho"
    )

    private val typosMap = mapOf(
        "pyhton" to "python",
        "pythn" to "python",
        "java scrpit" to "javascript",
        "javascrpt" to "javascript",
        "comuter" to "computer",
        "computr" to "computer",
        "algoritm" to "algorithm",
        "algrithm" to "algorithm",
        "algotithm" to "algorithm",
        "flowchrt" to "flowchart",
        "binry" to "binary",
        "progam" to "program",
        "progamming" to "programming",
        "databse" to "database",
        "netwrok" to "network",
        "softwere" to "software",
        "hardwere" to "hardware",
        "intenet" to "internet"
    )

    fun route(query: String, previousQuerySubject: String? = null): RoutedQuery {
        val trimmed = query.trim()
        val normalized = trimmed.lowercase()

        // 1. Typo correction
        var corrected = normalized
        for ((wrong, right) in typosMap) {
            corrected = corrected.replace(wrong, right)
        }

        // 2. Language Detection
        val words = normalized.split(Regex("[\\s,?.!]+")).filter { it.isNotBlank() }
        val romanCount = words.count { it in romanUrduKeywords }
        val isUrduScript = query.any { it in '\u0600'..'\u06FF' }

        val detectedLanguage = when {
            isUrduScript -> "Urdu"
            romanCount >= 1 || words.any { it.endsWith("o") || it.endsWith("ha") || it.endsWith("hai") } -> "Roman Urdu"
            else -> "English"
        }

        // 3. Follow-up detection (e.g. "simple samjhao", "ROM ka?", "aur batao", "explain in detail")
        val isFollowUp = words.contains("ka") && words.size <= 3 ||
                normalized.contains("simple samjhao") ||
                normalized.contains("aur batao") ||
                normalized.contains("detail se") ||
                normalized.contains("more details") ||
                normalized.contains("explain simply") ||
                (previousQuerySubject != null && words.size <= 2)

        // Resolve subject if it is a follow up like "ROM ka?"
        val resolvedQuery = if (isFollowUp && previousQuerySubject != null && !normalized.contains(previousQuerySubject.lowercase())) {
            "$corrected (in relation to $previousQuerySubject)"
        } else {
            corrected
        }

        // 4. Fast Concept matching
        var matchedConcept = FastConceptData.findConcept(corrected)

        // If follow up like "ROM ka?", extract "rom"
        if (matchedConcept == null && isFollowUp) {
            val potentialConcept = words.firstOrNull { it !in romanUrduKeywords && it.length >= 2 }
            if (potentialConcept != null) {
                matchedConcept = FastConceptData.findConcept(potentialConcept)
            }
        }

        // 5. Intent Classification
        var comparisonPair: Pair<String, String>? = null
        val intent = when {
            normalized.contains(" vs ") || normalized.contains(" difference between ") || normalized.contains(" farq ") || normalized.contains(" versus ") -> {
                val parts = if (normalized.contains(" vs ")) normalized.split(" vs ")
                else if (normalized.contains(" versus ")) normalized.split(" versus ")
                else normalized.split(" and ", " aur ")
                if (parts.size >= 2) {
                    comparisonPair = Pair(parts[0].trim(), parts[1].trim())
                }
                QueryIntent.COMPARISON
            }
            normalized.contains("def ") || normalized.contains("class ") || normalized.contains("import ") ||
            normalized.contains("error") || normalized.contains("bug") || normalized.contains("fix") ||
            normalized.contains("loop in python") || normalized.contains("code") || normalized.contains("syntax") -> {
                QueryIntent.CODING_DEBUGGING
            }
            normalized.contains("convert") || normalized.contains("to binary") || normalized.contains("to decimal") ||
            normalized.contains("to hex") || normalized.contains("calculate") || normalized.contains("sum of") -> {
                QueryIntent.MATH_CONVERSION
            }
            normalized.contains("mcq") || normalized.contains("quiz") || normalized.contains("test") || normalized.contains("question paper") -> {
                QueryIntent.QUIZ_PRACTICE
            }
            normalized.contains("exam") || normalized.contains("short question") || normalized.contains("long question") || normalized.contains("paper") -> {
                QueryIntent.COURSE_EXAM_QUESTION
            }
            isFollowUp -> QueryIntent.FOLLOW_UP
            matchedConcept != null && (words.size <= 4 || normalized.startsWith("what is") || normalized.contains("kya hai") || normalized.contains("kia ha")) -> {
                QueryIntent.DEFINITION
            }
            matchedConcept != null -> QueryIntent.CONCEPT_EXPLANATION
            words.size <= 1 -> QueryIntent.AMBIGUOUS
            else -> QueryIntent.CONCEPT_EXPLANATION
        }

        return RoutedQuery(
            rawQuery = trimmed,
            normalizedQuery = normalized,
            correctedQuery = resolvedQuery,
            detectedLanguage = detectedLanguage,
            intent = intent,
            matchedFastConcept = matchedConcept,
            isFollowUp = isFollowUp,
            comparisonSubjects = comparisonPair
        )
    }
}
