package com.example.piyo.data.repository.education

import com.example.piyo.data.mapper.education.EducationContentMapper
import com.example.piyo.data.remote.EducationContentService
import com.example.piyo.domain.model.EducationContent
import com.example.piyo.domain.repository.EducationContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class EducationContentRepositoryImpl(
    private val service: EducationContentService,
    private val mapper: EducationContentMapper
) : EducationContentRepository {

    override fun observeEducationContents(): Flow<Result<List<EducationContent>>> {
        return service.observeEducationContents()
            .map { dtos ->
                Result.success(dtos.map { mapper.toDomain(it) })
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override suspend fun searchEducationContents(query: String): Result<List<EducationContent>> {
        return try {
            val dtos = service.searchEducationContents(query)
            Result.success(dtos.map { mapper.toDomain(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

