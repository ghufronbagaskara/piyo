package com.example.piyo.data.dto.quizresult

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class QuizResultDto(
    @DocumentId
    val id: String? = null,
    val userId: String? = null,
    val childId: String? = null,
    val childAge: Int? = null,
    val quizId: String? = null,
    val segmentUsed: String? = null,
    val score: Int? = null,
    val totalQuestions: Int? = null,
    val correctAnswers: Int? = null,
    val answers: List<AnswerDto>? = null,
    val completedAt: Timestamp? = null,
    val duration: Int? = null,
    val recommendedContent: List<String>? = null
)

data class AnswerDto(
    val questionId: String? = null,
    val selectedAnswer: String? = null,
    val isCorrect: Boolean? = null
)
