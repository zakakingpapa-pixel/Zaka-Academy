package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.course.CourseRepository
import com.example.data.db.*
import com.example.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ZakaRepository(
    private val database: AppDatabase,
    private val context: Context
) {
    private val dao = database.zakaDao()
    private val prefs: SharedPreferences = context.getSharedPreferences("zaka_prefs", Context.MODE_PRIVATE)

    fun getAllChapters(): List<ChapterItem> = CourseRepository.allChapters

    fun getTopic(topicId: String): TopicNote? = CourseRepository.getTopic(topicId)

    fun getChapter(id: Int): ChapterItem? = CourseRepository.getChapter(id)

    fun getAllMcqs(): List<McqItem> = CourseRepository.getAllMcqs()

    fun getMcqsForChapter(chapterId: Int): List<McqItem> = CourseRepository.getMcqsForChapter(chapterId)

    fun getAllFlashcards(): List<FlashcardItem> = CourseRepository.getAllFlashcards()

    fun getFlashcardsForChapter(chapterId: Int): List<FlashcardItem> = CourseRepository.getFlashcardsForChapter(chapterId)

    // Topic progress
    fun getTopicProgress(): Flow<List<TopicProgressEntity>> = dao.getAllTopicProgress()

    suspend fun markTopicCompleted(topicId: String, chapterId: Int) {
        dao.saveTopicProgress(
            TopicProgressEntity(
                topicId = topicId,
                chapterId = chapterId,
                isCompleted = true,
                lastReadTimestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun toggleBookmark(topicId: String, chapterId: Int, isBookmarked: Boolean) {
        dao.saveTopicProgress(
            TopicProgressEntity(
                topicId = topicId,
                chapterId = chapterId,
                isBookmarked = isBookmarked,
                lastReadTimestamp = System.currentTimeMillis()
            )
        )
    }

    // Quiz scores
    fun getQuizResults(): Flow<List<QuizResultEntity>> = dao.getAllQuizResults()

    suspend fun saveQuizResult(quizTitle: String, chapterId: Int, score: Int, total: Int) {
        val pct = if (total > 0) (score.toFloat() / total) * 100f else 0f
        dao.saveQuizResult(
            QuizResultEntity(
                quizTitle = quizTitle,
                chapterId = chapterId,
                score = score,
                totalQuestions = total,
                percentage = pct
            )
        )
    }

    // Flashcard mastery
    fun getFlashcardMastery(): Flow<List<FlashcardMasteryEntity>> = dao.getAllFlashcardMastery()

    suspend fun setFlashcardMastery(flashcardId: String, chapterId: Int, isMastered: Boolean) {
        dao.saveFlashcardMastery(
            FlashcardMasteryEntity(
                flashcardId = flashcardId,
                chapterId = chapterId,
                isMastered = isMastered,
                lastReviewedTimestamp = System.currentTimeMillis()
            )
        )
    }

    // Chat history
    fun getChatHistory(): Flow<List<ChatMessage>> = dao.getRecentChatHistory().map { list ->
        list.map { entity ->
            ChatMessage(
                id = entity.id.toString(),
                sender = if (entity.sender == "USER") MessageSender.USER else MessageSender.AI,
                text = entity.messageText,
                timestamp = entity.timestamp,
                providerUsed = entity.providerUsed,
                isFallback = entity.isFallback,
                detectedLanguage = entity.detectedLanguage
            )
        }
    }

    suspend fun saveChatMessage(message: ChatMessage) {
        dao.insertChatMessage(
            ChatHistoryEntity(
                sender = if (message.sender == MessageSender.USER) "USER" else "AI",
                messageText = message.text,
                timestamp = message.timestamp,
                providerUsed = message.providerUsed,
                isFallback = message.isFallback,
                detectedLanguage = message.detectedLanguage
            )
        )
    }

    suspend fun clearChatHistory() {
        dao.clearChatHistory()
    }

    // Written practice attempts
    fun getWrittenAttempts(): Flow<List<WrittenAttemptEntity>> = dao.getAllWrittenAttempts()

    suspend fun saveWrittenAttempt(
        question: PracticeQuestion,
        studentAnswer: String,
        evaluation: AnswerEvaluation,
        hintsUsed: Int
    ) {
        dao.saveWrittenAttempt(
            WrittenAttemptEntity(
                questionId = question.id,
                chapterId = question.chapterId,
                topicId = question.topicId,
                questionType = question.type.name,
                studentAnswer = studentAnswer,
                scorePercent = evaluation.scorePercent,
                verdict = evaluation.verdict.name,
                evaluatedBy = evaluation.evaluatedBy,
                hintsUsed = hintsUsed
            )
        )
    }

    // Exams
    fun getExamResults(): Flow<List<ExamResultEntity>> = dao.getAllExamResults()

    suspend fun saveExamSummary(summary: ExamSummary, chapterIds: List<Int>) {
        dao.saveExamResult(
            ExamResultEntity(
                title = summary.title,
                mode = summary.mode.name,
                chapterIds = chapterIds.joinToString(","),
                totalQuestions = summary.totalQuestions,
                scorePercent = summary.scorePercent,
                correct = summary.correct,
                partial = summary.partial,
                incorrect = summary.incorrect
            )
        )
    }

    // Daily challenge
    fun getDailyChallenges(): Flow<List<DailyChallengeEntity>> = dao.getRecentDailyChallenges()

    suspend fun saveDailyChallenge(date: String, questionId: String, scorePercent: Int) {
        dao.saveDailyChallenge(
            DailyChallengeEntity(
                date = date,
                questionId = questionId,
                isCompleted = true,
                scorePercent = scorePercent
            )
        )
    }

    // User streak
    fun getUserStreak(): Flow<UserStreakEntity?> = dao.getUserStreak()

    /**
     * Records activity for today. The streak grows only when the previous active day was yesterday,
     * stays put on a repeat visit the same day, and restarts otherwise.
     */
    suspend fun recordActivityForStreak(current: UserStreakEntity?) {
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US)
        val today = formatter.format(java.util.Date())
        val yesterday = formatter.format(java.util.Date(System.currentTimeMillis() - 86_400_000L))

        if (current?.lastActiveDate == today) return

        val days = when (current?.lastActiveDate) {
            yesterday -> (current.currentStreakDays) + 1
            else -> 1
        }

        dao.saveUserStreak(
            (current ?: UserStreakEntity()).copy(
                id = 1,
                currentStreakDays = days,
                lastActiveDate = today
            )
        )
    }

    // Settings
    fun loadSettings(): AppSettings {
        val providerStr = prefs.getString("provider", AiProviderType.OFFLINE_EXPERT.name) ?: AiProviderType.OFFLINE_EXPERT.name
        val provider = try { AiProviderType.valueOf(providerStr) } catch (e: Exception) { AiProviderType.OFFLINE_EXPERT }
        val styleStr = prefs.getString("style", TeachingStyle.BALANCED.name) ?: TeachingStyle.BALANCED.name
        val style = try { TeachingStyle.valueOf(styleStr) } catch (e: Exception) { TeachingStyle.BALANCED }

        return AppSettings(
            providerType = provider,
            geminiApiKey = prefs.getString("gemini_key", "") ?: "",
            ollamaEndpoint = prefs.getString("ollama_endpoint", "http://192.168.1.100:11434") ?: "http://192.168.1.100:11434",
            ollamaModel = prefs.getString("ollama_model", "qwen2.5-coder:7b") ?: "qwen2.5-coder:7b",
            teachingStyle = style,
            preferredLanguage = prefs.getString("language", "Auto") ?: "Auto"
        )
    }

    fun saveSettings(settings: AppSettings) {
        prefs.edit()
            .putString("provider", settings.providerType.name)
            .putString("gemini_key", settings.geminiApiKey)
            .putString("ollama_endpoint", settings.ollamaEndpoint)
            .putString("ollama_model", settings.ollamaModel)
            .putString("style", settings.teachingStyle.name)
            .putString("language", settings.preferredLanguage)
            .apply()
    }
}
