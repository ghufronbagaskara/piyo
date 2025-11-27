package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.repository.QuizRepository

class GetQuizByAgeUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(childAge: Int): Result<Quiz> {
        val segment = when (childAge) {
            in 2..5 -> "balita"
            in 6..9 -> "sd"
            in 10..12 -> "praremaja"
            in 13..17 -> "remaja"
            else -> return Result.failure(Exception("Usia anak tidak valid untuk quiz"))
        }

        return repository.getQuizByAgeSegment(segment)
    }
}

