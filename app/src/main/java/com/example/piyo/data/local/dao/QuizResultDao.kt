package com.example.piyo.data.local.dao

import androidx.room.*
import com.example.piyo.data.local.entity.QuizResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

    @Query("SELECT * FROM quiz_results WHERE childId = :childId ORDER BY completedAt DESC")
    fun getQuizResultsByChildId(childId: String): Flow<List<QuizResultEntity>>

    @Query("SELECT * FROM quiz_results WHERE userId = :userId ORDER BY completedAt DESC LIMIT :limit")
    fun getRecentQuizResults(userId: String, limit: Int = 10): Flow<List<QuizResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results WHERE isSynced = 0")
    suspend fun getUnsyncedResults(): List<QuizResultEntity>

    @Query("UPDATE quiz_results SET isSynced = 1 WHERE id = :resultId")
    suspend fun markAsSynced(resultId: Long)

    @Query("DELETE FROM quiz_results WHERE completedAt < :timestamp")
    suspend fun deleteOldResults(timestamp: Long)

    @Query("SELECT AVG(score * 100.0 / totalQuestions) FROM quiz_results WHERE childId = :childId")
    suspend fun getAverageScore(childId: String): Double?
}

