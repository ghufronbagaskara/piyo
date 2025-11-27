package com.example.piyo.data.dto.quiz

import com.google.firebase.Timestamp

data class QuizDto(
    val id: String? = null,
    val title: String? = null,
    val thumbnail: String? = null,
    val description: String? = null,
    val totalQuestions: Int? = null,
    val duration: Int? = null,
    val difficulty: String? = null,
    val createdBy: String? = null,
    val createdAt: Timestamp? = null,
    val tags: List<String>? = null
)
