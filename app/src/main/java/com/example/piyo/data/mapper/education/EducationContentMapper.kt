package com.example.piyo.data.mapper.education

import com.example.piyo.data.dto.education.EducationContentDto
import com.example.piyo.domain.model.EducationContent

class EducationContentMapper {
    fun toDomain(dto: EducationContentDto): EducationContent {
        return EducationContent(
            id = dto.id.orEmpty(),
            type = dto.type.orEmpty(),
            title = dto.title.orEmpty(),
            thumbnail = dto.thumbnail.orEmpty(),
            description = dto.description.orEmpty(),
            duration = dto.duration.orEmpty(),
            createdAt = dto.createdAt?.seconds ?: 0L,
            tags = dto.tags.orEmpty()
        )
    }
}

