package com.example.piyo.domain.repository

import com.example.piyo.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserProgressRepository {
    suspend fun saveProgress(progress: UserProgress): Result<String>
    suspend fun updateProgress(progress: UserProgress): Result<Unit>
    suspend fun getProgressByUserId(userId: String): Flow<List<UserProgress>>
    suspend fun getProgressByChildId(childId: String): Flow<List<UserProgress>>
    suspend fun getProgressByContentId(userId: String, contentId: String): Result<UserProgress?>
}

