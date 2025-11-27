package com.example.piyo.domain.usecase.quiz

import com.example.piyo.domain.model.EducationContent
import com.example.piyo.domain.repository.EducationContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecommendedContentsUseCase(
    private val repository: EducationContentRepository
) {
    operator fun invoke(ageSegment: String): Flow<List<EducationContent>> {
        val tags = getTagsBySegment(ageSegment)

        return repository.observeEducationContents().map { result ->
            result.getOrNull()?.filter { content ->
                content.tags.any { tag -> tags.contains(tag.lowercase()) }
            }?.take(3) ?: emptyList()
        }
    }

    private fun getTagsBySegment(segment: String): List<String> {
        return when (segment) {
            "balita" -> listOf("communication", "tantrum", "routine", "play", "development")
            "sd" -> listOf("school", "social", "learning", "friendship", "behavior")
            "praremaja" -> listOf("independence", "emotion regulation", "social skills", "puberty")
            "remaja" -> listOf("independence", "emotion regulation", "career", "relationships", "self-advocacy")
            else -> emptyList()
        }
    }
}
