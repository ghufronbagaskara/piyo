package com.example.piyo.domain.usecase.child

import com.example.piyo.domain.model.Child
import com.example.piyo.domain.repository.ChildRepository
import kotlinx.coroutines.flow.Flow

class GetChildrenByParentIdUseCase(
    private val repository: ChildRepository
) {
    suspend operator fun invoke(parentId: String): Flow<List<Child>> {
        return repository.getChildrenByParentId(parentId)
    }
}

