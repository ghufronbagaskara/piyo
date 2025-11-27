package com.example.piyo.data.mapper.quizresult

import com.example.piyo.data.dto.quizresult.AnswerDto
import com.example.piyo.data.dto.quizresult.QuizResultDto
import com.example.piyo.domain.model.Answer
import com.example.piyo.domain.model.QuizResult
import com.google.firebase.Timestamp

class QuizResultMapper {
    fun toDomain(dto: QuizResultDto): QuizResult {
        return QuizResult(
            id = dto.id.orEmpty(),
            userId = dto.userId.orEmpty(),
            childId = dto.childId.orEmpty(),
            childAge = dto.childAge ?: 0,
            quizId = dto.quizId.orEmpty(),
            segmentUsed = dto.segmentUsed.orEmpty(),
            score = dto.score ?: 0,
            totalQuestions = dto.totalQuestions ?: 0,
            correctAnswers = dto.correctAnswers ?: 0,
            answers = dto.answers?.map { answerToDomain(it) } ?: emptyList(),
            completedAt = dto.completedAt?.seconds ?: 0L,
            duration = dto.duration ?: 0,
            recommendedContent = dto.recommendedContent ?: emptyList()
        )
    }

    private fun answerToDomain(dto: AnswerDto): Answer {
        return Answer(
            questionId = dto.questionId.orEmpty(),
            selectedAnswer = dto.selectedAnswer.orEmpty(),
            isCorrect = dto.isCorrect ?: false
        )
    }

    fun toDto(model: QuizResult): Map<String, Any> {
        return mapOf(
            "userId" to model.userId,
            "childId" to model.childId,
            "childAge" to model.childAge,
            "quizId" to model.quizId,
            "segmentUsed" to model.segmentUsed,
            "score" to model.score,
            "totalQuestions" to model.totalQuestions,
            "correctAnswers" to model.correctAnswers,
            "answers" to model.answers.map { answerToDto(it) },
            "completedAt" to Timestamp.now(),
            "duration" to model.duration,
            "recommendedContent" to model.recommendedContent
        )
    }

    private fun answerToDto(answer: Answer): Map<String, Any> {
        return mapOf(
            "questionId" to answer.questionId,
            "selectedAnswer" to answer.selectedAnswer,
            "isCorrect" to answer.isCorrect
        )
    }
}
