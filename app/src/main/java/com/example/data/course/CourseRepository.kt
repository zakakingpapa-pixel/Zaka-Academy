package com.example.data.course

import com.example.model.ChapterItem
import com.example.model.FlashcardItem
import com.example.model.McqItem
import com.example.model.TopicNote

object CourseRepository {
    val allChapters: List<ChapterItem> = listOf(
        Chapter1Fundamentals.chapter,
        Chapter2Algorithms.chapter,
        Chapter3DataRepresentation.chapter,
        Chapter4Architecture.chapter,
        Chapter5OperatingSystems.chapter,
        Chapter6Networks.chapter,
        Chapter7Cybersecurity.chapter,
        Chapter8Programming.chapter
    )

    fun getAllTopics(): List<TopicNote> = allChapters.flatMap { it.topics }

    fun getChapter(id: Int): ChapterItem? = allChapters.firstOrNull { it.id == id }

    fun getTopic(topicId: String): TopicNote? = getAllTopics().firstOrNull { it.topicId == topicId }

    fun getAllMcqs(): List<McqItem> = getAllTopics().flatMap { it.mcqs }

    fun getMcqsForChapter(chapterId: Int): List<McqItem> =
        allChapters.firstOrNull { it.id == chapterId }?.topics?.flatMap { it.mcqs } ?: emptyList()

    fun getAllFlashcards(): List<FlashcardItem> = getAllTopics().flatMap { it.flashcards }

    fun getFlashcardsForChapter(chapterId: Int): List<FlashcardItem> =
        allChapters.firstOrNull { it.id == chapterId }?.topics?.flatMap { it.flashcards } ?: emptyList()

    /**
     * Searches across all chapters, topic definitions, points, and terms.
     */
    fun searchCourse(query: String): List<TopicNote> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()

        return getAllTopics().filter { topic ->
            topic.title.lowercase().contains(q) ||
            topic.definition.lowercase().contains(q) ||
            topic.easyExplanation.lowercase().contains(q) ||
            topic.chapterTitle.lowercase().contains(q) ||
            topic.keyTerms.any { it.first.lowercase().contains(q) || it.second.lowercase().contains(q) } ||
            topic.relatedConcepts.any { it.lowercase().contains(q) }
        }
    }
}
