package com.example.piyo.domain.usecase.child

import com.example.piyo.domain.repository.ChildRepository

class UploadBabyPhotoUseCase(
    private val repository: ChildRepository
) {
    suspend operator fun invoke(childId: String, photoData: ByteArray): Result<String> {
        return repository.uploadBabyPhoto(childId, photoData)
    }
}

