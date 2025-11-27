package com.example.piyo.data.repository.user

import com.example.piyo.data.mapper.user.UserMapper
import com.example.piyo.data.remote.UserService
import com.example.piyo.domain.model.User
import com.example.piyo.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val service: UserService,
    private val mapper: UserMapper
) : UserRepository {

    override suspend fun createUser(user: User): Result<String> {
        return try {
            val data = mapper.toDto(user, isUpdate = false)
            service.createUser(user.id, data)
            Result.success(user.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return try {
            val data = mapper.toDto(user, isUpdate = true)
            service.updateUser(user.id, data)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(userId: String): Result<User> {
        return try {
            val dto = service.getUserById(userId)
            if (dto != null) {
                Result.success(mapper.toDomain(dto))
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun observeUser(userId: String): Flow<User?> {
        return service.observeUser(userId).map { dto ->
            dto?.let { mapper.toDomain(it) }
        }
    }

    override suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            service.deleteUser(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

