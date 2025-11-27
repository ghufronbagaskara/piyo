package com.example.piyo.domain.model

data class QuizItem(
    val id: String = "",
    val title: String = "",
    val thumbnail: String = "",
    val description: String = "",
    val totalQuestions: Int = 0,
    val duration: Int = 0,
    val difficulty: String = "",
    val tags: List<String> = emptyList()
)

