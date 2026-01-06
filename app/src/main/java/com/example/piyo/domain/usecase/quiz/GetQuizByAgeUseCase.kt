package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.repository.QuizRepository

class GetQuizByAgeUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(childAge: Int): Result<Quiz> {
        val segment = when (childAge) {
            in 2..4 -> "2-4"
            in 5..7 -> "5-7"
            in 8..12 -> "8-12"
            in 13..17 -> "13-17"
            else -> "18+" // For 18 years old and above (including 22 year old)
        }

        return repository.getQuizByAgeSegment(segment)
    }
}
