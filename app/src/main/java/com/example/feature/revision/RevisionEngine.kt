package com.example.feature.revision

import com.example.data.course.CourseRepository
import com.example.data.course.QuestionBank
import com.example.data.db.QuizResultEntity
import com.example.data.db.TopicProgressEntity
import com.example.data.db.WrittenAttemptEntity
import com.example.model.PracticeQuestion
import kotlin.math.roundToInt

data class ChapterMastery(
    val chapterId: Int,
    val chapterTitle: String,
    val topicsCompleted: Int,
    val topicsTotal: Int,
    /** null when the learner has not attempted anything in this chapter yet. */
    val averageScore: Int?,
    val attempts: Int
) {
    val hasData: Boolean get() = averageScore != null
    val isWeak: Boolean get() = averageScore != null && averageScore < 60
}

data class LearningSnapshot(
    val chapters: List<ChapterMastery>,
    val totalAttempts: Int,
    val overallScore: Int?,
    val weakChapterIds: List<Int>,
    val recommendations: List<String>
)

/**
 * Turns recorded progress into an honest study picture. Chapters with no recorded attempt report
 * `null` rather than a made-up score, and recommendations are only produced from real data.
 */
object RevisionEngine {

    fun snapshot(
        topicProgress: List<TopicProgressEntity>,
        quizResults: List<QuizResultEntity>,
        writtenAttempts: List<WrittenAttemptEntity>
    ): LearningSnapshot {
        val chapters = CourseRepository.allChapters.map { chapter ->
            val topicIds = chapter.topics.map { it.topicId }.toSet()
            val completed = topicProgress.count { it.topicId in topicIds && it.isCompleted }

            val quizScores = quizResults.filter { it.chapterId == chapter.id }.map { it.percentage.toDouble() }
            val writtenScores = writtenAttempts.filter { it.chapterId == chapter.id }.map { it.scorePercent.toDouble() }
            val allScores = quizScores + writtenScores

            ChapterMastery(
                chapterId = chapter.id,
                chapterTitle = chapter.title,
                topicsCompleted = completed,
                topicsTotal = topicIds.size,
                averageScore = if (allScores.isEmpty()) null else allScores.average().roundToInt(),
                attempts = allScores.size
            )
        }

        val allScores = quizResults.map { it.percentage.toDouble() } + writtenAttempts.map { it.scorePercent.toDouble() }
        val weak = chapters.filter { it.isWeak }.sortedBy { it.averageScore }

        val recommendations = buildList {
            weak.take(3).forEach {
                add("Revise ${it.chapterTitle} — average ${it.averageScore}% across ${it.attempts} attempt(s).")
            }
            chapters.filter { !it.hasData }.take(2).forEach {
                add("No attempts recorded for ${it.chapterTitle} yet — try its chapter test.")
            }
            chapters.filter { it.topicsTotal > 0 && it.topicsCompleted < it.topicsTotal }.take(2).forEach {
                add("Finish reading ${it.chapterTitle} (${it.topicsCompleted}/${it.topicsTotal} topics read).")
            }
            if (isEmpty()) add("Nothing recorded yet — start with any chapter test to build your progress data.")
        }

        return LearningSnapshot(
            chapters = chapters,
            totalAttempts = allScores.size,
            overallScore = if (allScores.isEmpty()) null else allScores.average().roundToInt(),
            weakChapterIds = weak.map { it.chapterId },
            recommendations = recommendations
        )
    }

    /**
     * Questions worth revising: weakest chapters first, then anything never attempted.
     */
    fun revisionQuestions(
        snapshot: LearningSnapshot,
        writtenAttempts: List<WrittenAttemptEntity>,
        limit: Int = 20
    ): List<PracticeQuestion> {
        val failedIds = writtenAttempts.filter { it.scorePercent < 60 }.map { it.questionId }.toSet()
        val attemptedIds = writtenAttempts.map { it.questionId }.toSet()

        val failed = QuestionBank.allQuestions.filter { it.id in failedIds }
        val weakChapter = QuestionBank.allQuestions.filter {
            it.chapterId in snapshot.weakChapterIds && it.id !in attemptedIds
        }
        val unattempted = QuestionBank.allQuestions.filter { it.id !in attemptedIds }

        return (failed + weakChapter + unattempted).distinctBy { it.id }.take(limit)
    }

    /**
     * Deterministic question of the day: the same [dateKey] always yields the same question so the
     * daily challenge is stable across app restarts.
     */
    fun dailyChallenge(dateKey: String): PracticeQuestion? {
        val pool = QuestionBank.allQuestions
        if (pool.isEmpty()) return null
        val index = (dateKey.hashCode().toLong() and 0x7fffffff) % pool.size
        return pool[index.toInt()]
    }
}
