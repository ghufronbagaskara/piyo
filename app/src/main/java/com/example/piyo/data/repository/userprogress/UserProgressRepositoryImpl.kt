package com.example.piyo.data.repository.userprogress

import com.example.piyo.data.mapper.userprogress.UserProgressMapper
import com.example.piyo.data.remote.UserProgressService
import com.example.piyo.domain.model.UserProgress
import com.example.piyo.domain.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProgressRepositoryImpl(
    private val service: UserProgressService,
    private val mapper: UserProgressMapper
) : UserProgressRepository {

    override suspend fun saveProgress(progress: UserProgress): Result<String> {
        return try {
            val data = mapper.toDto(progress, isUpdate = false)
            val progressId = service.saveProgress(data)
            Result.success(progressId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProgress(progress: UserProgress): Result<Unit> {
        return try {
            val data = mapper.toDto(progress, isUpdate = true)
            service.updateProgress(progress.id, data)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProgressByUserId(userId: String): Flow<List<UserProgress>> {
        return service.observeProgressByUserId(userId).map { dtos ->
            dtos.map { mapper.toDomain(it) }
        }
    }

    override suspend fun getProgressByChildId(childId: String): Flow<List<UserProgress>> {
        return service.observeProgressByChildId(childId).map { dtos ->
            dtos.map { mapper.toDomain(it) }
        }
    }

    override suspend fun getProgressByContentId(userId: String, contentId: String): Result<UserProgress?> {
        return try {
            val dto = service.getProgressByContentId(userId, contentId)
            Result.success(dto?.let { mapper.toDomain(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

