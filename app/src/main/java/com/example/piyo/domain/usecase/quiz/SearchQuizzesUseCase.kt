package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.QuizItem
import com.example.piyo.domain.repository.QuizRepository

class SearchQuizzesUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(query: String): Result<List<QuizItem>> {
        return repository.searchQuizzes(query)
    }
}
