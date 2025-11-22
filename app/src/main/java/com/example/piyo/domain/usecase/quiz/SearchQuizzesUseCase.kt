package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.repository.QuizRepository

class SearchQuizzesUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(query: String): Result<List<Quiz>> {
        return repository.searchQuizzes(query)
    }
}

