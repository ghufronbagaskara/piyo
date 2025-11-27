package com.example.piyo.domain.usecase.child

import com.example.piyo.domain.repository.ChildRepository
import kotlinx.coroutines.flow.first

class CheckUserHasChildUseCase(
    private val repository: ChildRepository
) {
    suspend operator fun invoke(parentId: String): Boolean {
        return try {
            val children = repository.getChildrenByParentId(parentId).first()
            children.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}

