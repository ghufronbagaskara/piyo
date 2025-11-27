package com.example.piyo.domain.repository

import com.example.piyo.domain.model.Child
import kotlinx.coroutines.flow.Flow

interface ChildRepository {
    suspend fun createChild(child: Child): Result<String>
    suspend fun updateChild(child: Child): Result<Unit>
    suspend fun getChildById(childId: String): Result<Child>
    suspend fun getChildrenByParentId(parentId: String): Flow<List<Child>>
    suspend fun uploadProfilePhoto(childId: String, data: ByteArray): Result<String>
    suspend fun uploadBabyPhoto(childId: String, data: ByteArray): Result<String>
    suspend fun deleteChild(childId: String): Result<Unit>
}

