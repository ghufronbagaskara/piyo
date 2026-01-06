package com.example.piyo.data.mapper.plan

import com.example.piyo.data.dto.plan.PlanTaskDto
import com.example.piyo.domain.model.PlanTask

class PlanTaskMapper {
    fun toDomain(dto: PlanTaskDto): PlanTask {
        return PlanTask(
            id = dto.id.orEmpty(),
            childId = dto.childId.orEmpty(),
            parentId = dto.parentId.orEmpty(),
            title = dto.title.orEmpty(),
            time = dto.time.orEmpty(),
            description = dto.description.orEmpty(),
            date = dto.date.orEmpty(),
            isCompleted = dto.isCompleted ?: false,
            createdAt = dto.createdAt?.seconds ?: 0L,
            updatedAt = dto.updatedAt?.seconds ?: 0L
        )
    }

    fun toDto(model: PlanTask): Map<String, Any> {
        return hashMapOf(
            "childId" to model.childId,
            "parentId" to model.parentId,
            "title" to model.title,
            "time" to model.time,
            "description" to model.description,
            "date" to model.date,
            "isCompleted" to model.isCompleted,
            "createdAt" to com.google.firebase.Timestamp.now(),
            "updatedAt" to com.google.firebase.Timestamp.now()
        )
    }
}

