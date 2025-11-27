package com.example.piyo.domain.usecase.child

import com.example.piyo.domain.model.Child
import com.example.piyo.domain.repository.ChildRepository

class CreateOrUpdateChildUseCase(
    private val repository: ChildRepository
) {
    suspend operator fun invoke(child: Child): Result<String> {
        return try {
            if (child.fullName.isBlank()) {
                return Result.failure(Exception("Nama lengkap tidak boleh kosong"))
            }
            if (child.birthDate.isBlank()) {
                return Result.failure(Exception("Tanggal lahir tidak boleh kosong"))
            }
            if (child.gender.isBlank()) {
                return Result.failure(Exception("Jenis kelamin harus dipilih"))
            }

            if (child.id.isBlank()) {
                repository.createChild(child)
            } else {
                repository.updateChild(child)
                Result.success(child.id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

