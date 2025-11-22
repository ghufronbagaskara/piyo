package com.example.piyo.domain.repository

import com.example.piyo.domain.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun observeQuizzes(): Flow<Result<List<Quiz>>>
    suspend fun searchQuizzes(query: String): Result<List<Quiz>>
}

