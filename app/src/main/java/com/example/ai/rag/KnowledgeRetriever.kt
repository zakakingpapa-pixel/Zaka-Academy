package com.example.ai.rag

import com.example.ai.router.RoutedQuery
import com.example.data.course.CourseRepository
import com.example.model.TopicNote

data class RetrievedKnowledge(
    val matchedTopic: TopicNote?,
    val relatedTopics: List<TopicNote>,
    val courseExcerpt: String,
    val examPoints: List<String>,
    val shortQuestions: List<Pair<String, String>>
)

object KnowledgeRetriever {

    fun retrieve(routedQuery: RoutedQuery): RetrievedKnowledge {
        val query = routedQuery.correctedQuery
        val matchedConcept = routedQuery.matchedFastConcept

        // 1. Search in Course Repository
        val courseResults = CourseRepository.searchCourse(query)
        val directTopic = courseResults.firstOrNull() ?: if (matchedConcept != null) {
            CourseRepository.getAllTopics().firstOrNull { topic ->
                topic.title.contains(matchedConcept.name, ignoreCase = true) ||
                topic.relatedConcepts.any { it.equals(matchedConcept.id, ignoreCase = true) }
            }
        } else null

        val excerptBuilder = StringBuilder()
        val examPoints = mutableListOf<String>()
        val shortQuestions = mutableListOf<Pair<String, String>>()

        if (directTopic != null) {
            excerptBuilder.appendLine("Course Chapter: ${directTopic.chapterTitle}")
            excerptBuilder.appendLine("Topic: ${directTopic.title}")
            excerptBuilder.appendLine("Definition: ${directTopic.definition}")
            excerptBuilder.appendLine("Easy Explanation: ${directTopic.easyExplanation}")
            excerptBuilder.appendLine("Key Points: ${directTopic.importantPoints.joinToString("; ")}")

            examPoints.addAll(directTopic.examFocusedPoints)
            directTopic.shortQuestions.take(3).forEach {
                shortQuestions.add(Pair(it.question, it.answer))
            }
        }

        return RetrievedKnowledge(
            matchedTopic = directTopic,
            relatedTopics = courseResults.take(3),
            courseExcerpt = excerptBuilder.toString(),
            examPoints = examPoints,
            shortQuestions = shortQuestions
        )
    }
}
