package com.example.piyo.domain.usecase.child

import com.example.piyo.data.repository.ChildRepositoryWithCache
import com.example.piyo.domain.model.Child
import kotlinx.coroutines.flow.Flow

/**
 * Use Case untuk mendapatkan children dengan offline-first strategy
 */
class GetChildrenWithCacheUseCase(
    private val repository: ChildRepositoryWithCache
) {
    operator fun invoke(parentId: String): Flow<List<Child>> {
        return repository.getChildrenByParentId(parentId)
    }
}

