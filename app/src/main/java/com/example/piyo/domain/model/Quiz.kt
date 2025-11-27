package com.example.piyo.domain.model

data class Quiz(
    val id: String = "",
    val ageSegment: String = "",
    val title: String = "",
    val duration: Int = 0,
    val questions: List<Question> = emptyList()
)

data class Question(
    val aspect: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = 0,
    val reason: String = ""
)

