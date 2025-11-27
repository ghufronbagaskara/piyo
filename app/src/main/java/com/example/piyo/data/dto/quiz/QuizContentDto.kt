package com.example.piyo.data.dto.quiz

import com.google.firebase.firestore.DocumentId

data class QuizDto(
    @DocumentId
    val id: String? = null,
    val ageSegment: String? = null,
    val title: String? = null,
    val duration: Int? = null,
    val questions: List<QuestionDto>? = null
)

data class QuestionDto(
    val aspect: String? = null,
    val question: String? = null,
    val options: List<String>? = null,
    val correctAnswerIndex: Int? = null,
    val reason: String? = null
)

