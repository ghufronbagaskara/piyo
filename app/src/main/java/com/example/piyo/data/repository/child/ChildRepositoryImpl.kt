package com.example.piyo.data.repository.child

import com.example.piyo.data.mapper.child.ChildMapper
import com.example.piyo.data.remote.ChildService
import com.example.piyo.domain.model.Child
import com.example.piyo.domain.repository.ChildRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChildRepositoryImpl(
    private val service: ChildService,
    private val mapper: ChildMapper
) : ChildRepository {

    override suspend fun createChild(child: Child): Result<String> {
        return try {
            val data = mapper.toDto(child, isUpdate = false)
            val childId = service.createChild(data)
            Result.success(childId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateChild(child: Child): Result<Unit> {
        return try {
            val data = mapper.toDto(child, isUpdate = true)
            service.updateChild(child.id, data)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getChildById(childId: String): Result<Child> {
        return try {
            val dto = service.getChildById(childId)
            if (dto != null) {
                Result.success(mapper.toDomain(dto))
            } else {
                Result.failure(Exception("Child not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getChildrenByParentId(parentId: String): Flow<List<Child>> {
        return service.observeChildrenByParentId(parentId).map { dtos ->
            dtos.map { mapper.toDomain(it) }
        }
    }

    override suspend fun uploadProfilePhoto(childId: String, data: ByteArray): Result<String> {
        return try {
            val url = service.uploadProfilePhoto(childId, data)
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadBabyPhoto(childId: String, data: ByteArray): Result<String> {
        return try {
            val url = service.uploadBabyPhoto(childId, data)
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteChild(childId: String): Result<Unit> {
        return try {
            service.deleteChild(childId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

