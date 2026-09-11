package com.example

import com.example.data.course.CourseRepository
import com.example.data.course.QuestionBank
import com.example.data.db.QuizResultEntity
import com.example.data.db.TopicProgressEntity
import com.example.data.db.WrittenAttemptEntity
import com.example.feature.errorsolver.ErrorSolver
import com.example.feature.exam.ExamEngine
import com.example.feature.revision.RevisionEngine
import com.example.feature.tester.AnswerEvaluator
import com.example.model.AnswerVerdict
import com.example.model.ExamAnswer
import com.example.model.ExamMode
import com.example.model.QuestionType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class QuestionTesterUnitTest {

    private val shortQuestion = QuestionBank.allQuestions.first { it.type == QuestionType.SHORT }

    @Test
    fun questionBank_coversEveryChapter() {
        CourseRepository.allChapters.forEach { chapter ->
            assertTrue(
                "Chapter ${chapter.id} has no practice questions",
                QuestionBank.forChapter(chapter.id).isNotEmpty()
            )
        }
    }

    @Test
    fun questionBank_idsAreUnique() {
        val ids = QuestionBank.allQuestions.map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun evaluate_modelAnswerScoresAsCorrect() {
        val result = AnswerEvaluator.evaluate(shortQuestion, shortQuestion.modelAnswer)
        assertEquals(AnswerVerdict.CORRECT, result.verdict)
        assertTrue(result.scorePercent >= 75)
        assertFalse(result.isAiEvaluation)
    }

    @Test
    fun evaluate_irrelevantAnswerIsIncorrect() {
        val result = AnswerEvaluator.evaluate(
            shortQuestion,
            "My favourite colour is blue and I like playing cricket after school."
        )
        assertEquals(AnswerVerdict.INCORRECT, result.verdict)
    }

    @Test
    fun evaluate_partialAnswerScoresBetween() {
        val half = shortQuestion.modelAnswer.take(shortQuestion.modelAnswer.length / 3)
        val result = AnswerEvaluator.evaluate(shortQuestion, half)
        assertTrue(result.scorePercent in 1..99)
    }

    @Test
    fun evaluate_emptyAnswerIsNotAttempted() {
        val result = AnswerEvaluator.evaluate(shortQuestion, "   ")
        assertEquals(AnswerVerdict.NOT_ATTEMPTED, result.verdict)
        assertEquals(0, result.scorePercent)
    }

    @Test
    fun evaluate_isNotExactTextMatching() {
        val reworded = AnswerEvaluator.significantTerms(shortQuestion.modelAnswer)
            .take(6)
            .joinToString(" and ") { "the $it matters here" }
        val result = AnswerEvaluator.evaluate(shortQuestion, reworded)
        assertTrue("Reworded answer should still earn credit", result.scorePercent > 0)
    }

    @Test
    fun hints_areProgressiveAndThree() {
        val hints = AnswerEvaluator.hints(shortQuestion)
        assertEquals(3, hints.size)
        assertTrue(hints.all { it.isNotBlank() })
        assertTrue(hints[2].startsWith("Key concept"))
    }

    @Test
    fun examEngine_buildsRequestedNumberOfQuestions() {
        val config = ExamEngine.chapterTest(1, questionCount = 5)
        val paper = ExamEngine.buildPaper(config, random = Random(42))
        assertEquals(5, paper.size)
        assertTrue(paper.all { it.chapterId == 1 })
    }

    @Test
    fun examEngine_revisionModeUsesWeakChapters() {
        val config = ExamEngine.fullMockExam(questionCount = 6).copy(mode = ExamMode.REVISION)
        val paper = ExamEngine.buildPaper(config, weakChapterIds = listOf(3), random = Random(7))
        assertTrue(paper.isNotEmpty())
        assertTrue(paper.all { it.chapterId == 3 })
    }

    @Test
    fun examEngine_summarizesMcqAnswers() {
        val mcqs = QuestionBank.allQuestions.filter { it.type == QuestionType.MCQ }.take(2)
        val answers = listOf(
            ExamAnswer(mcqs[0], selectedOptionIndex = mcqs[0].correctOptionIndex),
            ExamAnswer(mcqs[1], selectedOptionIndex = (mcqs[1].correctOptionIndex + 1) % mcqs[1].options.size)
        )
        val summary = ExamEngine.summarize(ExamEngine.fullMockExam(), answers)
        assertEquals(2, summary.totalQuestions)
        assertEquals(1, summary.correct)
        assertEquals(50, summary.scorePercent)
    }

    @Test
    fun revisionEngine_reportsNoScoreWithoutAttempts() {
        val snapshot = RevisionEngine.snapshot(emptyList(), emptyList(), emptyList())
        assertEquals(null, snapshot.overallScore)
        assertTrue(snapshot.chapters.all { it.averageScore == null })
        assertTrue(snapshot.weakChapterIds.isEmpty())
    }

    @Test
    fun revisionEngine_flagsWeakChapterFromRecordedData() {
        val snapshot = RevisionEngine.snapshot(
            topicProgress = listOf(TopicProgressEntity(topicId = "t", chapterId = 1, isCompleted = true)),
            quizResults = listOf(
                QuizResultEntity(quizTitle = "q", chapterId = 2, score = 2, totalQuestions = 10, percentage = 20f)
            ),
            writtenAttempts = listOf(
                WrittenAttemptEntity(
                    questionId = "x", chapterId = 2, topicId = "t", questionType = "SHORT",
                    studentAnswer = "a", scorePercent = 30, verdict = "INCORRECT", evaluatedBy = "test"
                )
            )
        )
        assertTrue(snapshot.weakChapterIds.contains(2))
        assertEquals(25, snapshot.chapters.first { it.chapterId == 2 }.averageScore)
    }

    @Test
    fun dailyChallenge_isStableForSameDate() {
        val a = RevisionEngine.dailyChallenge("2026-01-05")
        val b = RevisionEngine.dailyChallenge("2026-01-05")
        assertNotNull(a)
        assertEquals(a?.id, b?.id)
    }

    @Test
    fun errorSolver_identifiesKnownPythonError() {
        val analysis = ErrorSolver.analyze(
            "Traceback (most recent call last):\n  File \"main.py\", line 4\n    print(x)\nNameError: name 'x' is not defined"
        )
        assertTrue(analysis.observedFacts.any { it.contains("line 4") })
        assertTrue(analysis.likelyCauses.any { it.contains("defined") })
        assertTrue(analysis.needsExecution.isNotEmpty())
    }

    @Test
    fun errorSolver_doesNotInventCauseForUnknownText() {
        val analysis = ErrorSolver.analyze("it just does not work")
        assertTrue(analysis.likelyCauses.any { it.contains("No known error signature") })
    }
}
