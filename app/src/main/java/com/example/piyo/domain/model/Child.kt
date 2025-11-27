package com.example.piyo.domain.model

data class Child(
    val id: String = "",
    val parentId: String = "",
    val fullName: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val babyPhotoUrl: String = "",
    val birthWeight: Double = 0.0,
    val diagnosisHistory: String = "",
    val profilePhotoUrl: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)

