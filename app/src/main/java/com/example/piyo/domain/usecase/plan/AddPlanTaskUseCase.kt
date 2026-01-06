package com.example.piyo.domain.usecase.plan

import com.example.piyo.domain.model.PlanTask
import com.example.piyo.domain.repository.PlanTaskRepository

class AddPlanTaskUseCase(
    private val repository: PlanTaskRepository
) {
    suspend operator fun invoke(task: PlanTask): Result<String> {
        return repository.addTask(task)
    }
}

