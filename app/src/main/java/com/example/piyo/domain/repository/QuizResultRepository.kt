package com.example.piyo.domain.repository

import com.example.piyo.domain.model.QuizResult
import kotlinx.coroutines.flow.Flow

interface QuizResultRepository {
    suspend fun saveQuizResult(result: QuizResult): Result<String>
    suspend fun getQuizResultsByUserId(userId: String): Flow<List<QuizResult>>
    suspend fun getQuizResultsByChildId(childId: String): Flow<List<QuizResult>>
    suspend fun getQuizResultById(resultId: String): Result<QuizResult>
    suspend fun getLatestQuizResult(userId: String): Result<QuizResult?>
}
