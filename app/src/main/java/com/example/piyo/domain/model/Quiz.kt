package com.example.piyo.domain.model

data class Quiz(
    val id: String = "",
    val title: String = "",
    val thumbnail: String = "",
    val description: String = "",
    val totalQuestions: Int = 0,
    val duration: Int = 0, // in seconds
    val difficulty: String = "", // "beginner", "intermediate", "advanced"
    val createdAt: Long = 0L,
    val tags: List<String> = emptyList()
)

