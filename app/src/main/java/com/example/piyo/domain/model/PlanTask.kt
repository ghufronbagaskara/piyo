package com.example.piyo.domain.model

data class PlanTask(
    val id: String = "",
    val childId: String = "",
    val parentId: String = "",
    val title: String = "",
    val time: String = "",
    val description: String = "",
    val date: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)

