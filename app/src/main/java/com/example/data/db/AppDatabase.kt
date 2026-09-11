package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        TopicProgressEntity::class,
        QuizResultEntity::class,
        FlashcardMasteryEntity::class,
        ChatHistoryEntity::class,
        UserStreakEntity::class,
        WrittenAttemptEntity::class,
        ExamResultEntity::class,
        DailyChallengeEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun zakaDao(): ZakaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /** Adds the practice/exam tables without touching existing learner progress. */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `written_attempts` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `questionId` TEXT NOT NULL,
                        `chapterId` INTEGER NOT NULL,
                        `topicId` TEXT NOT NULL,
                        `questionType` TEXT NOT NULL,
                        `studentAnswer` TEXT NOT NULL,
                        `scorePercent` INTEGER NOT NULL,
                        `verdict` TEXT NOT NULL,
                        `evaluatedBy` TEXT NOT NULL,
                        `hintsUsed` INTEGER NOT NULL,
                        `timestamp` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `exam_results` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `title` TEXT NOT NULL,
                        `mode` TEXT NOT NULL,
                        `chapterIds` TEXT NOT NULL,
                        `totalQuestions` INTEGER NOT NULL,
                        `scorePercent` INTEGER NOT NULL,
                        `correct` INTEGER NOT NULL,
                        `partial` INTEGER NOT NULL,
                        `incorrect` INTEGER NOT NULL,
                        `timestamp` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `daily_challenges` (
                        `date` TEXT PRIMARY KEY NOT NULL,
                        `questionId` TEXT NOT NULL,
                        `isCompleted` INTEGER NOT NULL,
                        `scorePercent` INTEGER NOT NULL,
                        `timestamp` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "zaka_academy_database"
                )
                .addMigrations(MIGRATION_1_2)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
