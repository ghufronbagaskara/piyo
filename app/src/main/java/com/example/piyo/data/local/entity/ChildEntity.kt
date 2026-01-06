package com.example.piyo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.piyo.domain.model.Child

@Entity(tableName = "children")
data class ChildEntity(
    @PrimaryKey
    val id: String,
    val parentId: String,
    val fullName: String,
    val birthDate: String,
    val gender: String,
    val birthWeight: Double,
    val diagnosisHistory: String,
    val profilePhotoUrl: String,
    val babyPhotoUrl: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)

// Converter functions
fun ChildEntity.toDomain(): Child {
    return Child(
        id = id,
        parentId = parentId,
        fullName = fullName,
        birthDate = birthDate,
        gender = gender,
        birthWeight = birthWeight,
        diagnosisHistory = diagnosisHistory,
        profilePhotoUrl = profilePhotoUrl,
        babyPhotoUrl = babyPhotoUrl
    )
}

fun Child.toEntity(isSynced: Boolean = false): ChildEntity {
    return ChildEntity(
        id = id,
        parentId = parentId,
        fullName = fullName,
        birthDate = birthDate,
        gender = gender,
        birthWeight = birthWeight,
        diagnosisHistory = diagnosisHistory,
        profilePhotoUrl = profilePhotoUrl,
        babyPhotoUrl = babyPhotoUrl,
        isSynced = isSynced
    )
}

