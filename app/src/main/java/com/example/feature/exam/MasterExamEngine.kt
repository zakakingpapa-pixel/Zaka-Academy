package com.example.feature.exam

import com.example.model.*

/**
 * ZAKA CLASS 9 MASTER EXAM ENGINE
 * Complete exam system with detailed evaluation
 */

data class ExamSession(
    val id: String,
    val title: String,
    val totalQuestions: Int,
    val timeLimit: Int, // minutes
    val questions: List<McqItem>,
    var answeredCount: Int = 0,
    var correctCount: Int = 0,
    var skippedCount: Int = 0
)

data class ExamResult(
    val sessionId: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skipped: Int,
    val scorePercentage: Float,
    val grade: String,
    val weakTopics: List<String>,
    val strongTopics: List<String>,
    val recommendations: List<String>
)

class MasterExamEngine {

    fun createChapter1Exam(): ExamSession {
        return ExamSession(
            id = "exam_ch1_001",
            title = "Chapter 1: Computer Fundamentals - Full Test",
            totalQuestions = 25,
            timeLimit = 45,
            questions = listOf(
                McqItem(
                    id = "q1",
                    question = "IPO میں 'P' کا مطلب کیا ہے؟",
                    options = listOf("Processing", "Printing", "Program", "Processor"),
                    correctOptionIndex = 0,
                    explanation = "Processing - CPU میں ڈیٹا پر کام کرنا"
                ),
                McqItem(
                    id = "q2",
                    question = "Hardware کی مثال کیا ہے؟",
                    options = listOf("CPU", "Windows", "Excel", "Game"),
                    correctOptionIndex = 0,
                    explanation = "CPU ایک hardware component ہے"
                ),
                McqItem(
                    id = "q3",
                    question = "Software کیا ہے؟",
                    options = listOf("بغیر وزن کا", "Program اور applications", "فزیکل حصے", "کوئی نہیں"),
                    correctOptionIndex = 1,
                    explanation = "Software پروگرام اور applications ہیں جو کمپیوٹر میں چلتے ہیں"
                ),
                McqItem(
                    id = "q4",
                    question = "BIOS کہاں محفوظ ہے؟",
                    options = listOf("RAM", "ROM", "HDD", "Cache"),
                    correctOptionIndex = 1,
                    explanation = "BIOS ROM میں محفوظ ہے"
                ),
                McqItem(
                    id = "q5",
                    question = "Input device کیا ہے؟",
                    options = listOf("مانیٹر", "پرنٹر", "کیبورڈ", "سپیکر"),
                    correctOptionIndex = 2,
                    explanation = "کیبورڈ input device ہے جس سے ڈیٹا لیا جاتا ہے"
                )
            )
        )
    }

    fun evaluateExam(session: ExamSession, answers: Map<String, Int>): ExamResult {
        var correct = 0
        var wrong = 0
        var skipped = 0
        val wrongTopics = mutableSetOf<String>()
        val correctTopics = mutableSetOf<String>()

        session.questions.forEach { question ->
            val userAnswer = answers[question.id]
            when {
                userAnswer == null -> skipped++
                userAnswer == question.correctOptionIndex -> {
                    correct++
                    correctTopics.add(question.question.split(" ").first())
                }
                else -> {
                    wrong++
                    wrongTopics.add(question.question.split(" ").first())
                }
            }
        }

        val scorePercentage = (correct.toFloat() / session.totalQuestions) * 100
        val grade = when {
            scorePercentage >= 90 -> "A+" 
            scorePercentage >= 80 -> "A"
            scorePercentage >= 70 -> "B"
            scorePercentage >= 60 -> "C"
            scorePercentage >= 50 -> "D"
            else -> "F"
        }

        val recommendations = when {
            scorePercentage >= 90 -> listOf(
                "🎉 بہترین نتیجہ!",
                "آپ اگلے chapter پر جا سکتے ہیں",
                "مشکل سوالات کے لیے advanced material پڑھیں"
            )
            scorePercentage >= 70 -> listOf(
                "✅ اچھا نتیجہ!",
                "ضعیف topics کو دوبارہ پڑھیں",
                "مزید practice کریں",
                "Quiz وقتاً فوقتاً دیتے رہیں"
            )
            else -> listOf(
                "⚠️ مزید محنت کریں",
                "پورا chapter دوبارہ پڑھیں",
                "ہر topic کے notes اچھے سے بنائیں",
                "Flashcards سے رٹے",
                "AI Teacher سے سوالات کریں"
            )
        }

        return ExamResult(
            sessionId = session.id,
            totalQuestions = session.totalQuestions,
            correctAnswers = correct,
            wrongAnswers = wrong,
            skipped = skipped,
            scorePercentage = scorePercentage,
            grade = grade,
            weakTopics = wrongTopics.toList(),
            strongTopics = correctTopics.toList(),
            recommendations = recommendations
        )
    }

    fun generateAdaptiveQuestions(previousResult: ExamResult): List<McqItem> {
        // Based on weak topics, generate adaptive questions
        val adaptiveQuestions = mutableListOf<McqItem>()
        
        previousResult.weakTopics.forEach { topic ->
            // Generate practice questions for weak topics
            adaptiveQuestions.add(
                McqItem(
                    id = "adaptive_${topic}_1",
                    question = "$topic کے بارے میں تفصیلی سوال؟",
                    options = listOf("Option A", "Option B", "Option C", "Option D"),
                    correctOptionIndex = 0,
                    explanation = "یہ adaptive question آپ کے ضعیف topic سے ہے"
                )
            )
        }
        
        return adaptiveQuestions
    }
}
