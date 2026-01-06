package com.example.piyo.data.dto.plan

import com.google.firebase.Timestamp

data class PlanTaskDto(
    val id: String? = null,
    val childId: String? = null,
    val parentId: String? = null,
    val title: String? = null,
    val time: String? = null,
    val description: String? = null,
    val date: String? = null,
    val isCompleted: Boolean? = null,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)

