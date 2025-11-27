package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.QuizItem
import com.example.piyo.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class ObserveQuizzesUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(): Flow<Result<List<QuizItem>>> {
        return repository.observeQuizzes()
    }
}
