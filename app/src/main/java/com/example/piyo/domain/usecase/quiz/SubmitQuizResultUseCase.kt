package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.QuizResult
import com.example.piyo.domain.repository.QuizResultRepository

class SubmitQuizResultUseCase(
    private val repository: QuizResultRepository
) {
    suspend operator fun invoke(result: QuizResult): Result<String> {
        if (result.userId.isBlank()) {
            return Result.failure(Exception("User ID tidak boleh kosong"))
        }
        if (result.totalQuestions == 0) {
            return Result.failure(Exception("Quiz tidak valid"))
        }

        return repository.saveQuizResult(result)
    }
}

