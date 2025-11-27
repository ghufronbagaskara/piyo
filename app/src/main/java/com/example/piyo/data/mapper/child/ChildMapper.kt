package com.example.piyo.data.mapper.child

import com.example.piyo.data.dto.child.ChildDto
import com.example.piyo.domain.model.Child
import com.google.firebase.Timestamp

class ChildMapper {
    fun toDomain(dto: ChildDto): Child {
        return Child(
            id = dto.id.orEmpty(),
            parentId = dto.parentId.orEmpty(),
            fullName = dto.fullName.orEmpty(),
            birthDate = dto.birthDate.orEmpty(),
            gender = dto.gender.orEmpty(),
            babyPhotoUrl = dto.babyPhotoUrl.orEmpty(),
            birthWeight = dto.birthWeight ?: 0.0,
            diagnosisHistory = dto.diagnosisHistory.orEmpty(),
            profilePhotoUrl = dto.profilePhotoUrl.orEmpty(),
            createdAt = dto.createdAt?.seconds ?: 0L,
            updatedAt = dto.updatedAt?.seconds ?: 0L
        )
    }

    fun toDto(model: Child, isUpdate: Boolean = false): Map<String, Any> {
        val map = mutableMapOf<String, Any>(
            "fullName" to model.fullName,
            "birthDate" to model.birthDate,
            "gender" to model.gender,
            "birthWeight" to model.birthWeight,
            "diagnosisHistory" to model.diagnosisHistory
        )

        if (model.parentId.isNotBlank()) {
            map["parentId"] = model.parentId
        }

        if (model.babyPhotoUrl.isNotBlank()) {
            map["babyPhotoUrl"] = model.babyPhotoUrl
        }

        if (model.profilePhotoUrl.isNotBlank()) {
            map["profilePhotoUrl"] = model.profilePhotoUrl
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

