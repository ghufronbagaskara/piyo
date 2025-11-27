package com.example.piyo.domain.repository

import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.model.QuizItem
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getQuizByAgeSegment(ageSegment: String): Result<Quiz>
    suspend fun getAllQuizzes(): Flow<List<Quiz>>
    suspend fun observeQuizzes(): Flow<Result<List<QuizItem>>>
    suspend fun searchQuizzes(query: String): Result<List<QuizItem>>
}
