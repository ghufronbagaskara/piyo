package com.example.piyo.domain.repository

import com.example.piyo.domain.model.PlanTask
import kotlinx.coroutines.flow.Flow

interface PlanTaskRepository {
    suspend fun observeTasksByParentId(parentId: String): Flow<Result<List<PlanTask>>>
    suspend fun addTask(task: PlanTask): Result<String>
    suspend fun updateTask(task: PlanTask): Result<Unit>
    suspend fun deleteTask(taskId: String): Result<Unit>
    suspend fun toggleTaskCompletion(taskId: String, isCompleted: Boolean): Result<Unit>
}

