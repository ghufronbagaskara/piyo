package com.example.piyo.domain.model

data class EducationContent(
    val id: String = "",
    val type: String = "", // "video" or "article"
    val title: String = "",
    val thumbnail: String = "",
    val description: String = "",
    val duration: String = "", // format "mm:ss" for videos
    val createdAt: Long = 0L,
    val tags: List<String> = emptyList()
)

