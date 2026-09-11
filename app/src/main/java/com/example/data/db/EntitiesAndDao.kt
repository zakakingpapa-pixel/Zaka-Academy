package com.example.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "topic_progress")
data class TopicProgressEntity(
    @PrimaryKey val topicId: String,
    val chapterId: Int,
    val isCompleted: Boolean = false,
    val isBookmarked: Boolean = false,
    val lastReadTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quizTitle: String,
    val chapterId: Int,
    val score: Int,
    val totalQuestions: Int,
    val percentage: Float,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "flashcard_mastery")
data class FlashcardMasteryEntity(
    @PrimaryKey val flashcardId: String,
    val chapterId: Int,
    val isMastered: Boolean = false,
    val reviewCount: Int = 0,
    val lastReviewedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_history")
data class ChatHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // "USER" or "AI"
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val providerUsed: String = "Offline Expert",
    val isFallback: Boolean = false,
    val detectedLanguage: String = "English"
)

@Entity(tableName = "user_streak")
data class UserStreakEntity(
    @PrimaryKey val id: Int = 1,
    val currentStreakDays: Int = 1,
    val lastActiveDate: String = "",
    val totalQuestionsAnswered: Int = 0,
    val totalTopicsCompleted: Int = 0,
    val totalAiQueries: Int = 0
)

@Entity(tableName = "written_attempts")
data class WrittenAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionId: String,
    val chapterId: Int,
    val topicId: String,
    val questionType: String,
    val studentAnswer: String,
    val scorePercent: Int,
    val verdict: String,
    val evaluatedBy: String,
    val hintsUsed: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "exam_results")
data class ExamResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val mode: String,
    val chapterIds: String,
    val totalQuestions: Int,
    val scorePercent: Int,
    val correct: Int,
    val partial: Int,
    val incorrect: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "daily_challenges")
data class DailyChallengeEntity(
    @PrimaryKey val date: String,
    val questionId: String,
    val isCompleted: Boolean = false,
    val scorePercent: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface ZakaDao {
    @Query("SELECT * FROM topic_progress")
    fun getAllTopicProgress(): Flow<List<TopicProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopicProgress(progress: TopicProgressEntity)

    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    fun getAllQuizResults(): Flow<List<QuizResultEntity>>

    @Insert
    suspend fun saveQuizResult(result: QuizResultEntity)

    @Query("SELECT * FROM flashcard_mastery")
    fun getAllFlashcardMastery(): Flow<List<FlashcardMasteryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFlashcardMastery(mastery: FlashcardMasteryEntity)

    @Query("SELECT * FROM chat_history ORDER BY timestamp ASC LIMIT 100")
    fun getRecentChatHistory(): Flow<List<ChatHistoryEntity>>

    @Insert
    suspend fun insertChatMessage(message: ChatHistoryEntity)

    @Query("DELETE FROM chat_history")
    suspend fun clearChatHistory()

    @Query("SELECT * FROM user_streak WHERE id = 1")
    fun getUserStreak(): Flow<UserStreakEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserStreak(streak: UserStreakEntity)

    @Query("SELECT * FROM written_attempts ORDER BY timestamp DESC")
    fun getAllWrittenAttempts(): Flow<List<WrittenAttemptEntity>>

    @Insert
    suspend fun saveWrittenAttempt(attempt: WrittenAttemptEntity)

    @Query("SELECT * FROM exam_results ORDER BY timestamp DESC")
    fun getAllExamResults(): Flow<List<ExamResultEntity>>

    @Insert
    suspend fun saveExamResult(result: ExamResultEntity)

    @Query("SELECT * FROM daily_challenges ORDER BY date DESC LIMIT 30")
    fun getRecentDailyChallenges(): Flow<List<DailyChallengeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDailyChallenge(challenge: DailyChallengeEntity)
}
