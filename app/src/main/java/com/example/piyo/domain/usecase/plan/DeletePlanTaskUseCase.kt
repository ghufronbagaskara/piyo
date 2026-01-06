package com.example.piyo.domain.usecase.plan

import com.example.piyo.domain.repository.PlanTaskRepository

class DeletePlanTaskUseCase(
    private val repository: PlanTaskRepository
) {
    suspend operator fun invoke(taskId: String): Result<Unit> {
        return repository.deleteTask(taskId)
    }
}

