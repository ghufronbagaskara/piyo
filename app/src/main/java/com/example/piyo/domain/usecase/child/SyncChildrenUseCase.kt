package com.example.piyo.domain.usecase.child

import com.example.piyo.data.repository.ChildRepositoryWithCache

/**
 * Use Case untuk sync data children dari Firebase ke local database
 */
class SyncChildrenUseCase(
    private val repository: ChildRepositoryWithCache
) {
    suspend operator fun invoke(parentId: String): Result<Unit> {
        return repository.syncChildrenFromFirebase(parentId)
    }
}

