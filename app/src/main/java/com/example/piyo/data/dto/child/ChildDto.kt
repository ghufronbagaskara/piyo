package com.example.piyo.data.dto.child

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class ChildDto(
    @DocumentId
    val id: String? = null,
    val parentId: String? = null,
    val fullName: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val babyPhotoUrl: String? = null,
    val birthWeight: Double? = null,
    val diagnosisHistory: String? = null,
    val profilePhotoUrl: String? = null,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)

