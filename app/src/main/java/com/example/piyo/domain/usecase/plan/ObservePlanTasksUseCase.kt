package com.example.piyo.domain.usecase.plan

import com.example.piyo.domain.model.PlanTask
import com.example.piyo.domain.repository.PlanTaskRepository
import kotlinx.coroutines.flow.Flow

class ObservePlanTasksUseCase(
    private val repository: PlanTaskRepository
) {
    suspend operator fun invoke(parentId: String): Flow<Result<List<PlanTask>>> {
        return repository.observeTasksByParentId(parentId)
    }
}

