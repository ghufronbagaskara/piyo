package com.example.piyo.data.mapper.quiz

import com.example.piyo.data.dto.quiz.QuestionDto
import com.example.piyo.data.dto.quiz.ParentingQuizDto
import com.example.piyo.domain.model.Question
import com.example.piyo.domain.model.Quiz

class QuizMapper {
    fun toDomain(dto: ParentingQuizDto): Quiz {
        return Quiz(
            id = dto.id.orEmpty(),
            ageSegment = dto.ageSegment.orEmpty(),
            title = dto.title.orEmpty(),
            duration = dto.duration ?: 900,
            questions = dto.questions?.map { questionToDomain(it) } ?: emptyList()
        )
    }

    private fun questionToDomain(dto: QuestionDto): Question {
        return Question(
            aspect = dto.aspect.orEmpty(),
            question = dto.question.orEmpty(),
            options = dto.options ?: emptyList(),
            correctAnswerIndex = dto.correctAnswerIndex ?: 0,
            reason = dto.reason.orEmpty()
        )
    }

    fun toDto(quiz: Quiz): Map<String, Any> {
        return mapOf(
            "ageSegment" to quiz.ageSegment,
            "title" to quiz.title,
            "duration" to quiz.duration,
            "questions" to quiz.questions.map { questionToDto(it) }
        )
    }

    private fun questionToDto(question: Question): Map<String, Any> {
        return mapOf(
            "aspect" to question.aspect,
            "question" to question.question,
            "options" to question.options,
            "correctAnswerIndex" to question.correctAnswerIndex,
            "reason" to question.reason
        )
    }
}
