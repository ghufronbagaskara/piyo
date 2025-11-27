package com.example.piyo.data.dto.education

import com.google.firebase.Timestamp

data class EducationContentDto(
    val id: String? = null,
    val type: String? = null,
    val title: String? = null,
    val thumbnail: String? = null,
    val description: String? = null,
    val duration: String? = null,
    val createdBy: String? = null,
    val createdAt: Timestamp? = null,
    val tags: List<String>? = null
)
