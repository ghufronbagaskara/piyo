package com.example.piyo.data.repository.plan

import com.example.piyo.data.mapper.plan.PlanTaskMapper
import com.example.piyo.data.remote.PlanTaskService
import com.example.piyo.domain.model.PlanTask
import com.example.piyo.domain.repository.PlanTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PlanTaskRepositoryImpl(
    private val service: PlanTaskService,
    private val mapper: PlanTaskMapper
) : PlanTaskRepository {

    override suspend fun observeTasksByParentId(parentId: String): Flow<Result<List<PlanTask>>> {
        return service.observeTasksByParentId(parentId)
            .map { dtos ->
                Result.success(dtos.map { mapper.toDomain(it) })
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override suspend fun addTask(task: PlanTask): Result<String> {
        return service.addTask(mapper.toDto(task))
    }

    override suspend fun updateTask(task: PlanTask): Result<Unit> {
        return service.updateTask(task.id, mapper.toDto(task))
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return service.deleteTask(taskId)
    }

    override suspend fun toggleTaskCompletion(taskId: String, isCompleted: Boolean): Result<Unit> {
        return service.toggleTaskCompletion(taskId, isCompleted)
    }
}

