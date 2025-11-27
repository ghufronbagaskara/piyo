package com.example.piyo.domain.usecase.education

import com.example.piyo.domain.model.EducationContent
import com.example.piyo.domain.repository.EducationContentRepository

class SearchEducationContentsUseCase(
    private val repository: EducationContentRepository
) {
    suspend operator fun invoke(query: String): Result<List<EducationContent>> {
        return repository.searchEducationContents(query)
    }
}

