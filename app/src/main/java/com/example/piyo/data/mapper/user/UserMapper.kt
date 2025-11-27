package com.example.piyo.data.mapper.user

import com.example.piyo.data.dto.user.UserDto
import com.example.piyo.domain.model.User
import com.google.firebase.Timestamp

class UserMapper {
    fun toDomain(dto: UserDto): User {
        return User(
            id = dto.id.orEmpty(),
            email = dto.email.orEmpty(),
            fullName = dto.fullName.orEmpty(),
            photoUrl = dto.photoUrl.orEmpty(),
            role = dto.role ?: "parent",
            createdAt = dto.createdAt?.seconds ?: 0L,
            updatedAt = dto.updatedAt?.seconds ?: 0L
        )
    }

    fun toDto(model: User, isUpdate: Boolean = false): Map<String, Any> {
        val map = mutableMapOf<String, Any>(
            "email" to model.email,
            "fullName" to model.fullName,
            "role" to model.role
        )

        if (model.photoUrl.isNotBlank()) {
            map["photoUrl"] = model.photoUrl
        }

        if (isUpdate) {
            map["updatedAt"] = Timestamp.now()
        } else {
            map["createdAt"] = Timestamp.now()
            map["updatedAt"] = Timestamp.now()
        }

        return map
    }
}

