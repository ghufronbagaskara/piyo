package com.example.piyo.data.mapper.quiz

import com.example.piyo.data.dto.quiz.QuizDto
import com.example.piyo.domain.model.QuizItem

class QuizItemMapper {
    fun toDomain(dto: QuizDto): QuizItem {
        return QuizItem(
            id = dto.id.orEmpty(),
            title = dto.title.orEmpty(),
            thumbnail = dto.thumbnail.orEmpty(),
            description = dto.description.orEmpty(),
            totalQuestions = dto.totalQuestions ?: 0,
            duration = dto.duration ?: 0,
            difficulty = dto.difficulty.orEmpty(),
            tags = dto.tags ?: emptyList()
        )
    }
}

