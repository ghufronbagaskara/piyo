package com.example.piyo.data.dto.userprogress

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class UserProgressDto(
    @DocumentId
    val id: String? = null,
    val userId: String? = null,
    val childId: String? = null,
    val contentId: String? = null,
    val progress: Int? = null,
    val lastWatched: Timestamp? = null,
    val completed: Boolean? = null
)

