package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class ObserveQuizzesUseCase(
    private val repository: QuizRepository
) {
    operator fun invoke(): Flow<Result<List<Quiz>>> {
        return repository.observeQuizzes()
    }
}

