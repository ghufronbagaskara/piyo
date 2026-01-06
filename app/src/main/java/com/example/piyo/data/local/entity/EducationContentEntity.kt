package com.example.piyo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "education_contents")
data class EducationContentEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val videoUrl: String,
    val content: String,
    val ageRange: String,
    val tags: String, // JSON array as string
    val createdAt: Long,
    val cachedAt: Long = System.currentTimeMillis()
)

