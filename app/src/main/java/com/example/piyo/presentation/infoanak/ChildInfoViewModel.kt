package com.example.piyo.presentation.infoanak

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piyo.domain.model.Child
import com.example.piyo.domain.usecase.child.CreateOrUpdateChildUseCase
import com.example.piyo.domain.usecase.child.GetChildByIdUseCase
import com.example.piyo.domain.usecase.child.UploadBabyPhotoUseCase
import com.example.piyo.domain.usecase.child.UploadChildProfilePhotoUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

data class ChildInfoState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isUploadingProfile: Boolean = false,
    val isUploadingBaby: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val child: Child? = null,
    val fullName: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val birthWeight: String = "",
    val diagnosisHistory: String = "",
    val profilePhotoUri: Uri? = null,
    val babyPhotoUri: Uri? = null,
    val profilePhotoUrl: String = "",
    val babyPhotoUrl: String = ""
)

class ChildInfoViewModel(
    private val createOrUpdateChildUseCase: CreateOrUpdateChildUseCase,
    private val uploadChildProfilePhotoUseCase: UploadChildProfilePhotoUseCase,
    private val uploadBabyPhotoUseCase: UploadBabyPhotoUseCase,
    private val getChildByIdUseCase: GetChildByIdUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    var state by mutableStateOf(ChildInfoState())
        private set

    private var childId: String? = null

    fun loadChild(id: String?) {
        if (id == null) return
        childId = id
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            getChildByIdUseCase(id).fold(
                onSuccess = { child ->
                    state = state.copy(
                        isLoading = false,
                        child = child,
                        fullName = child.fullName,
                        birthDate = child.birthDate,
                        gender = child.gender,
                        birthWeight = if (child.birthWeight > 0) child.birthWeight.toString() else "",
                        diagnosisHistory = child.diagnosisHistory,
                        profilePhotoUrl = child.profilePhotoUrl,
                        babyPhotoUrl = child.babyPhotoUrl
                    )
                },
                onFailure = { error ->
                    state = state.copy(
                        isLoading = false,
                        error = error.message ?: "Gagal memuat data anak"
                    )
                }
            )
        }
    }

    fun onNameChange(name: String) {
        state = state.copy(fullName = name)
    }

    fun onBirthDateChange(date: String) {
        state = state.copy(birthDate = date)
    }

    fun onGenderChange(gender: String) {
        state = state.copy(gender = gender)
    }

    fun onBirthWeightChange(weight: String) {
        state = state.copy(birthWeight = weight)
    }

    fun onDiagnosisChange(diagnosis: String) {
        state = state.copy(diagnosisHistory = diagnosis)
    }

    fun onProfilePhotoSelected(uri: Uri) {
        state = state.copy(profilePhotoUri = uri)
    }

    fun onBabyPhotoSelected(uri: Uri) {
        state = state.copy(babyPhotoUri = uri)
    }

    fun uploadProfilePhoto(photoData: ByteArray, tempChildId: String) {
        viewModelScope.launch {
            state = state.copy(isUploadingProfile = true, error = null)
            uploadChildProfilePhotoUseCase(tempChildId, photoData).fold(
                onSuccess = { url ->
                    state = state.copy(
                        isUploadingProfile = false,
                        profilePhotoUrl = url
                    )
                },
                onFailure = { error ->
                    state = state.copy(
                        isUploadingProfile = false,
                        error = "Gagal upload foto profil: ${error.message}"
                    )
                }
            )
        }
    }

    fun uploadBabyPhoto(photoData: ByteArray, tempChildId: String) {
        viewModelScope.launch {
            state = state.copy(isUploadingBaby = true, error = null)
            uploadBabyPhotoUseCase(tempChildId, photoData).fold(
                onSuccess = { url ->
                    state = state.copy(
                        isUploadingBaby = false,
                        babyPhotoUrl = url
                    )
                },
                onFailure = { error ->
                    state = state.copy(
                        isUploadingBaby = false,
                        error = "Gagal upload foto bayi: ${error.message}"
                    )
                }
            )
        }
    }

    fun saveChild() {
        viewModelScope.launch {
            state = state.copy(isSaving = true, error = null)

            val currentUser = auth.currentUser
            if (currentUser == null) {
                state = state.copy(
                    isSaving = false,
                    error = "User tidak terautentikasi"
                )
                return@launch
            }

            val weight = state.birthWeight.toDoubleOrNull() ?: 0.0

            val child = Child(
                id = childId ?: "",
                parentId = currentUser.uid,
                fullName = state.fullName,
                birthDate = state.birthDate,
                gender = state.gender,
                birthWeight = weight,
                diagnosisHistory = state.diagnosisHistory,
                profilePhotoUrl = state.profilePhotoUrl,
                babyPhotoUrl = state.babyPhotoUrl
            )

            createOrUpdateChildUseCase(child).fold(
                onSuccess = { id ->
                    childId = id
                    state = state.copy(
                        isSaving = false,
                        success = true
                    )
                },
                onFailure = { error ->
                    state = state.copy(
                        isSaving = false,
                        error = error.message ?: "Gagal menyimpan data"
                    )
                }
            )
        }
    }

    fun clearError() {
        state = state.copy(error = null)
    }

    fun resetSuccess() {
        state = state.copy(success = false)
    }
}

