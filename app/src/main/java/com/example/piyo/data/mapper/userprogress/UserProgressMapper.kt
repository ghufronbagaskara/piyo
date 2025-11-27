package com.example.piyo.data.mapper.userprogress

import com.example.piyo.data.dto.userprogress.UserProgressDto
import com.example.piyo.domain.model.UserProgress
import com.google.firebase.Timestamp

class UserProgressMapper {
    fun toDomain(dto: UserProgressDto): UserProgress {
        return UserProgress(
            id = dto.id.orEmpty(),
            userId = dto.userId.orEmpty(),
            childId = dto.childId.orEmpty(),
            contentId = dto.contentId.orEmpty(),
            progress = dto.progress ?: 0,
            lastWatched = dto.lastWatched?.seconds ?: 0L,
            completed = dto.completed ?: false
        )
    }

    fun toDto(model: UserProgress, isUpdate: Boolean = false): Map<String, Any> {
        return mapOf(
            "userId" to model.userId,
            "childId" to model.childId,
            "contentId" to model.contentId,
            "progress" to model.progress,
            "lastWatched" to Timestamp.now(),
            "completed" to model.completed
        )
    }
}

