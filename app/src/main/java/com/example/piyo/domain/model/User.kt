package com.example.piyo.domain.model

data class User(
    val id: String = "",
    val email: String = "",
    val fullName: String = "",
    val photoUrl: String = "",
    val role: String = "parent",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)

