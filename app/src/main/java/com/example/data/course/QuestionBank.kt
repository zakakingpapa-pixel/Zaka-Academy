package com.example.data.course

import com.example.model.PracticeQuestion
import com.example.model.QuestionDifficulty
import com.example.model.QuestionSource
import com.example.model.QuestionType
import com.example.model.TopicNote

/**
 * Builds the practice question pool used by the Question Tester, chapter tests and the mock exam.
 *
 * Every question is derived from content that already exists in the course notes: short questions,
 * long questions, MCQs, comparisons and key terms. Nothing is invented here, and each item keeps a
 * [QuestionSource] so the UI can state where the question came from.
 */
object QuestionBank {

    val allQuestions: List<PracticeQuestion> by lazy {
        CourseRepository.getAllTopics().flatMap { buildForTopic(it) }
    }

    fun forChapter(chapterId: Int): List<PracticeQuestion> =
        allQuestions.filter { it.chapterId == chapterId }

    fun query(
        chapterIds: List<Int> = emptyList(),
        types: List<QuestionType> = emptyList(),
        difficulties: List<QuestionDifficulty> = emptyList()
    ): List<PracticeQuestion> = allQuestions.filter { q ->
        (chapterIds.isEmpty() || q.chapterId in chapterIds) &&
            (types.isEmpty() || q.type in types) &&
            (difficulties.isEmpty() || q.difficulty in difficulties)
    }

    fun byId(id: String): PracticeQuestion? = allQuestions.firstOrNull { it.id == id }

    private fun buildForTopic(topic: TopicNote): List<PracticeQuestion> {
        val items = mutableListOf<PracticeQuestion>()

        topic.mcqs.forEachIndexed { index, mcq ->
            items += base(
                topic = topic,
                id = "qb_mcq_${topic.topicId}_$index",
                type = QuestionType.MCQ,
                difficulty = difficultyOf(mcq.question + mcq.explanation),
                source = QuestionSource.ACADEMY_COURSE,
                question = mcq.question,
                modelAnswer = mcq.options.getOrElse(mcq.correctOptionIndex) { "" },
                keyPoints = listOf(mcq.explanation),
                options = mcq.options,
                correctOptionIndex = mcq.correctOptionIndex,
                explanation = mcq.explanation
            )
        }

        topic.shortQuestions.forEachIndexed { index, qa ->
            items += base(
                topic = topic,
                id = "qb_short_${topic.topicId}_$index",
                type = QuestionType.SHORT,
                difficulty = difficultyOf(qa.answer),
                source = QuestionSource.ACADEMY_COURSE,
                question = qa.question,
                modelAnswer = qa.answer,
                keyPoints = qa.keyPoints.ifEmpty { splitIntoPoints(qa.answer) }
            )
        }

        topic.longQuestions.forEachIndexed { index, qa ->
            items += base(
                topic = topic,
                id = "qb_long_${topic.topicId}_$index",
                type = QuestionType.LONG,
                difficulty = QuestionDifficulty.ADVANCED,
                source = QuestionSource.ACADEMY_COURSE,
                question = qa.question,
                modelAnswer = qa.answer,
                keyPoints = qa.keyPoints.ifEmpty { splitIntoPoints(qa.answer) }
            )
        }

        topic.keyTerms.forEachIndexed { index, (term, meaning) ->
            items += base(
                topic = topic,
                id = "qb_def_${topic.topicId}_$index",
                type = QuestionType.DEFINITION,
                difficulty = QuestionDifficulty.EASY,
                source = QuestionSource.ACADEMY_PRACTICE,
                question = "Define $term.",
                modelAnswer = meaning,
                keyPoints = splitIntoPoints(meaning)
            )
        }

        topic.comparisons.forEachIndexed { index, (pair, explanation) ->
            items += base(
                topic = topic,
                id = "qb_cmp_${topic.topicId}_$index",
                type = QuestionType.CONCEPTUAL,
                difficulty = QuestionDifficulty.MEDIUM,
                source = QuestionSource.ACADEMY_PRACTICE,
                question = "Explain the difference: $pair.",
                modelAnswer = explanation,
                keyPoints = splitIntoPoints(explanation)
            )
        }

        return items
    }

    private fun base(
        topic: TopicNote,
        id: String,
        type: QuestionType,
        difficulty: QuestionDifficulty,
        source: QuestionSource,
        question: String,
        modelAnswer: String,
        keyPoints: List<String>,
        options: List<String> = emptyList(),
        correctOptionIndex: Int = -1,
        explanation: String = ""
    ) = PracticeQuestion(
        id = id,
        chapterId = topic.chapterId,
        chapterTitle = topic.chapterTitle,
        topicId = topic.topicId,
        topicTitle = topic.title,
        type = type,
        difficulty = difficulty,
        source = source,
        question = question,
        modelAnswer = modelAnswer,
        keyPoints = keyPoints,
        options = options,
        correctOptionIndex = correctOptionIndex,
        explanation = explanation
    )

    /** Length-and-vocabulary heuristic; deliberately simple and deterministic. */
    private fun difficultyOf(text: String): QuestionDifficulty {
        val advancedMarkers = listOf(
            "architecture", "trade-off", "optimiz", "asymmetric", "virtual memory",
            "scheduling", "hexadecimal", "protocol stack", "pipeline"
        )
        val lower = text.lowercase()
        return when {
            advancedMarkers.any { lower.contains(it) } || text.length > 420 -> QuestionDifficulty.ADVANCED
            text.length > 180 -> QuestionDifficulty.MEDIUM
            else -> QuestionDifficulty.EASY
        }
    }

    private fun splitIntoPoints(answer: String): List<String> =
        answer.split('\n', ';')
            .flatMap { line -> if (line.length > 200) line.split(". ") else listOf(line) }
            .map { it.trim().trimEnd('.') }
            .filter { it.length > 12 }
            .take(6)
}
