package com.example.piyo.domain.model

data class UserProgress(
    val id: String = "",
    val userId: String = "",
    val childId: String = "",
    val contentId: String = "",
    val progress: Int = 0,
    val lastWatched: Long = 0L,
    val completed: Boolean = false
)

