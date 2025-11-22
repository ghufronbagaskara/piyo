package com.example.piyo.domain.usecase.education

import com.example.piyo.domain.model.EducationContent
import com.example.piyo.domain.repository.EducationContentRepository
import kotlinx.coroutines.flow.Flow

class ObserveEducationContentsUseCase(
    private val repository: EducationContentRepository
) {
    operator fun invoke(): Flow<Result<List<EducationContent>>> {
        return repository.observeEducationContents()
    }
}

