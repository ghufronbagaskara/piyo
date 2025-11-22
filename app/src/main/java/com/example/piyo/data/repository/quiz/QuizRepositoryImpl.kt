package com.example.piyo.data.repository.quiz

import com.example.piyo.data.mapper.quiz.QuizMapper
import com.example.piyo.data.remote.QuizService
import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl(
    private val service: QuizService,
    private val mapper: QuizMapper
) : QuizRepository {
    
    override fun observeQuizzes(): Flow<Result<List<Quiz>>> {
        return service.observeQuizzes()
            .map { dtos ->
                Result.success(dtos.map { mapper.toDomain(it) })
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }
    
    override suspend fun searchQuizzes(query: String): Result<List<Quiz>> {
        return try {
            val dtos = service.searchQuizzes(query)
            Result.success(dtos.map { mapper.toDomain(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

