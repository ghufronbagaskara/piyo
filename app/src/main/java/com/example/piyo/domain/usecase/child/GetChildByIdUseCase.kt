package com.example.piyo.domain.usecase.child

import com.example.piyo.domain.model.Child
import com.example.piyo.domain.repository.ChildRepository

class GetChildByIdUseCase(
    private val repository: ChildRepository
) {
    suspend operator fun invoke(childId: String): Result<Child> {
        return repository.getChildById(childId)
    }
}

