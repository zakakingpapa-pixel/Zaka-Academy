package com.example.feature.exam

import com.example.data.course.CourseRepository
import com.example.data.course.QuestionBank
import com.example.model.AnswerVerdict
import com.example.model.ChapterPerformance
import com.example.model.ExamAnswer
import com.example.model.ExamConfig
import com.example.model.ExamMode
import com.example.model.ExamSummary
import com.example.model.PracticeQuestion
import com.example.model.QuestionDifficulty
import com.example.model.QuestionType
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Builds chapter tests and full mock exams from [QuestionBank] and scores completed attempts.
 * Pure logic: no Android dependency, so it is unit-testable on the JVM.
 */
object ExamEngine {

    fun chapterTest(chapterId: Int, questionCount: Int = 15): ExamConfig {
        val chapter = CourseRepository.getChapter(chapterId)
        return ExamConfig(
            mode = ExamMode.PRACTICE,
            title = "Chapter Test: ${chapter?.title ?: "Chapter $chapterId"}",
            chapterIds = listOf(chapterId),
            types = QuestionType.entries.toList(),
            questionCount = questionCount
        )
    }

    fun fullMockExam(questionCount: Int = 30): ExamConfig = ExamConfig(
        mode = ExamMode.EXAM,
        title = "Full Mock Exam (All Chapters)",
        chapterIds = CourseRepository.allChapters.map { it.id },
        types = QuestionType.entries.toList(),
        questionCount = questionCount
    )

    /**
     * Selects the questions for a config. [weakChapterIds] only matters in REVISION mode, where the
     * paper is drawn from the learner's recorded weak chapters (falling back to the whole selection
     * when there is no recorded history yet).
     */
    fun buildPaper(
        config: ExamConfig,
        weakChapterIds: List<Int> = emptyList(),
        random: Random = Random.Default
    ): List<PracticeQuestion> {
        val difficulties = if (config.mode == ExamMode.CHALLENGE) {
            listOf(QuestionDifficulty.ADVANCED)
        } else {
            emptyList()
        }

        val chapterIds = when (config.mode) {
            ExamMode.REVISION -> weakChapterIds.ifEmpty { config.chapterIds }
            ExamMode.RANDOM -> emptyList()
            else -> config.chapterIds
        }

        var pool = QuestionBank.query(chapterIds, config.types, difficulties)
        if (pool.isEmpty()) pool = QuestionBank.query(chapterIds, config.types)
        if (pool.isEmpty()) pool = QuestionBank.allQuestions

        return pool.shuffled(random).take(config.questionCount)
    }

    fun summarize(config: ExamConfig, answers: List<ExamAnswer>): ExamSummary {
        val total = answers.size
        val scorePercent = if (total == 0) 0 else {
            (answers.sumOf { it.scoreFraction.toDouble() } / total * 100).roundToInt()
        }

        val correct = answers.count { answer ->
            answer.question.type == QuestionType.MCQ && answer.isCorrect ||
                answer.evaluation?.verdict == AnswerVerdict.CORRECT
        }
        val partial = answers.count { it.evaluation?.verdict == AnswerVerdict.PARTIALLY_CORRECT }
        val incorrect = total - correct - partial

        val breakdown = answers.groupBy { it.question.chapterId }
            .map { (chapterId, chapterAnswers) ->
                ChapterPerformance(
                    chapterId = chapterId,
                    chapterTitle = chapterAnswers.first().question.chapterTitle,
                    answered = chapterAnswers.size,
                    scorePercent = (chapterAnswers.sumOf { it.scoreFraction.toDouble() } / chapterAnswers.size * 100).roundToInt()
                )
            }
            .sortedBy { it.scorePercent }

        val weakAreas = answers
            .filter { it.scoreFraction < 0.6f }
            .map { it.question.topicTitle }
            .distinct()

        val recommendations = buildList {
            breakdown.filter { it.scorePercent < 60 }.take(3).forEach {
                add("Revise ${it.chapterTitle} — scored ${it.scorePercent}% on ${it.answered} question(s).")
            }
            if (weakAreas.isNotEmpty()) add("Re-read notes for: ${weakAreas.take(4).joinToString(", ")}.")
            if (isEmpty()) add("Solid attempt across every chapter in this paper — move on to a harder mode.")
        }

        return ExamSummary(
            title = config.title,
            mode = config.mode,
            totalQuestions = total,
            scorePercent = scorePercent,
            correct = correct,
            partial = partial,
            incorrect = incorrect.coerceAtLeast(0),
            chapterBreakdown = breakdown,
            weakAreas = weakAreas,
            recommendations = recommendations
        )
    }
}
