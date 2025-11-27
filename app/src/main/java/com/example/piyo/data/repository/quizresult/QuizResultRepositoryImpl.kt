package com.example.piyo.data.repository.quizresult

import com.example.piyo.data.mapper.quizresult.QuizResultMapper
import com.example.piyo.data.remote.QuizResultService
import com.example.piyo.domain.model.QuizResult
import com.example.piyo.domain.repository.QuizResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizResultRepositoryImpl(
    private val service: QuizResultService,
    private val mapper: QuizResultMapper
) : QuizResultRepository {

    override suspend fun saveQuizResult(result: QuizResult): Result<String> {
        return try {
            val data = mapper.toDto(result)
            val resultId = service.saveQuizResult(data)
            Result.success(resultId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getQuizResultsByUserId(userId: String): Flow<List<QuizResult>> {
        return service.observeQuizResultsByUserId(userId).map { dtos ->
            dtos.map { mapper.toDomain(it) }
        }
    }

    override suspend fun getQuizResultsByChildId(childId: String): Flow<List<QuizResult>> {
        return service.observeQuizResultsByChildId(childId).map { dtos ->
            dtos.map { mapper.toDomain(it) }
        }
    }

    override suspend fun getQuizResultById(resultId: String): Result<QuizResult> {
        return try {
            val dto = service.getQuizResultById(resultId)
            if (dto != null) {
                Result.success(mapper.toDomain(dto))
            } else {
                Result.failure(Exception("Quiz result not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getLatestQuizResult(userId: String): Result<QuizResult?> {
        return try {
            val dto = service.getLatestQuizResult(userId)
            Result.success(dto?.let { mapper.toDomain(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
