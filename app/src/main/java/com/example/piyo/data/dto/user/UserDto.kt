package com.example.piyo.data.dto.user

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class UserDto(
    @DocumentId
    val id: String? = null,
    val email: String? = null,
    val fullName: String? = null,
    val photoUrl: String? = null,
    val role: String? = null,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)

