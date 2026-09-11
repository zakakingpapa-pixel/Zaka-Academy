package com.example.model

/**
 * Kind of practice question presented by the Question Tester and the exam engine.
 */
enum class QuestionType(val label: String) {
    MCQ("MCQs"),
    SHORT("Short Questions"),
    LONG("Long Questions"),
    DEFINITION("Definition Questions"),
    CONCEPTUAL("Conceptual Questions")
}

enum class QuestionDifficulty(val label: String) {
    EASY("🟢 Easy"),
    MEDIUM("🟡 Medium"),
    ADVANCED("🔴 Advanced")
}

/**
 * Provenance of a question. The app never claims a question is an official past paper
 * or a guaranteed board question; every item carries the source it actually came from.
 */
enum class QuestionSource(val label: String) {
    ACADEMY_COURSE("Academy Course Question"),
    ACADEMY_PRACTICE("Academy Practice (derived from course notes)")
}

data class PracticeQuestion(
    val id: String,
    val chapterId: Int,
    val chapterTitle: String,
    val topicId: String,
    val topicTitle: String,
    val type: QuestionType,
    val difficulty: QuestionDifficulty,
    val source: QuestionSource,
    val question: String,
    val modelAnswer: String,
    val keyPoints: List<String> = emptyList(),
    val options: List<String> = emptyList(),
    val correctOptionIndex: Int = -1,
    val explanation: String = ""
) {
    val isWritten: Boolean get() = type != QuestionType.MCQ
}

enum class AnswerVerdict(val label: String, val symbol: String) {
    CORRECT("Correct", "✅"),
    PARTIALLY_CORRECT("Partially Correct", "🟡"),
    INCORRECT("Incorrect", "❌"),
    NOT_ATTEMPTED("Not Attempted", "⚪")
}

/**
 * Result of grading a written answer. [evaluatedBy] names the grader that actually ran so the
 * student is never told an AI model checked the answer when the offline rubric did.
 */
data class AnswerEvaluation(
    val verdict: AnswerVerdict,
    val scorePercent: Int,
    val coveredPoints: List<String>,
    val missingPoints: List<String>,
    val feedback: String,
    val modelAnswer: String,
    val evaluatedBy: String,
    val isAiEvaluation: Boolean
) {
    /** Shown next to every result: this is practice feedback, not an official board score. */
    val disclaimer: String = "AI Practice Evaluation — study feedback only, not an official board examiner score."
}

enum class ExamMode(val label: String, val description: String) {
    PRACTICE("Practice Mode", "Immediate feedback after every question"),
    EXAM("Exam Mode", "Feedback and score are shown after completion"),
    REVISION("Revision Mode", "Focuses on your weakest recorded chapters"),
    CHALLENGE("Challenge Mode", "Advanced difficulty questions only"),
    RANDOM("Random Mode", "Random mix from the whole course")
}

data class ExamConfig(
    val mode: ExamMode,
    val title: String,
    val chapterIds: List<Int>,
    val types: List<QuestionType>,
    val questionCount: Int
)

/**
 * A single answered item inside an exam attempt.
 */
data class ExamAnswer(
    val question: PracticeQuestion,
    val selectedOptionIndex: Int? = null,
    val writtenAnswer: String = "",
    val evaluation: AnswerEvaluation? = null
) {
    val isCorrect: Boolean
        get() = when {
            question.type == QuestionType.MCQ -> selectedOptionIndex == question.correctOptionIndex
            else -> evaluation?.verdict == AnswerVerdict.CORRECT
        }

    val scoreFraction: Float
        get() = when {
            question.type == QuestionType.MCQ -> if (isCorrect) 1f else 0f
            else -> (evaluation?.scorePercent ?: 0) / 100f
        }
}

data class ChapterPerformance(
    val chapterId: Int,
    val chapterTitle: String,
    val answered: Int,
    val scorePercent: Int
)

data class ExamSummary(
    val title: String,
    val mode: ExamMode,
    val totalQuestions: Int,
    val scorePercent: Int,
    val correct: Int,
    val partial: Int,
    val incorrect: Int,
    val chapterBreakdown: List<ChapterPerformance>,
    val weakAreas: List<String>,
    val recommendations: List<String>
)
