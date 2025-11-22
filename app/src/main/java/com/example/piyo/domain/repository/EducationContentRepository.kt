package com.example.piyo.domain.repository

import com.example.piyo.domain.model.EducationContent
import kotlinx.coroutines.flow.Flow

interface EducationContentRepository {
    fun observeEducationContents(): Flow<Result<List<EducationContent>>>
    suspend fun searchEducationContents(query: String): Result<List<EducationContent>>
}

