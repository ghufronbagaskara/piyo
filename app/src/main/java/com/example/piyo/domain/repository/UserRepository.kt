package com.example.piyo.domain.repository

import com.example.piyo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User): Result<String>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun getUserById(userId: String): Result<User>
    suspend fun observeUser(userId: String): Flow<User?>
    suspend fun deleteUser(userId: String): Result<Unit>
}

