package com.example.piyo.presentation.home.beranda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piyo.data.local.PreferencesManager
import com.example.piyo.domain.model.Child
import com.example.piyo.domain.usecase.auth.GetCurrentUserUseCase
import com.example.piyo.domain.usecase.child.GetChildrenByParentIdUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class BerandaUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val userEmail: String = "",
    val userPhotoUrl: String = "",
    val children: List<Child> = emptyList(),
    val selectedChild: Child? = null,
    val error: String? = null,
    val showWelcomePopup: Boolean = false
)

class BerandaViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getChildrenByParentIdUseCase: GetChildrenByParentIdUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    var uiState by mutableStateOf(BerandaUiState())
        private set

    init {
        // Check if user has seen welcome popup before
        val shouldShowWelcome = !preferencesManager.hasSeenWelcomePopup()
        uiState = uiState.copy(showWelcomePopup = shouldShowWelcome)

        loadUserData()
        loadChildrenData()
    }

    private fun loadUserData() {
        val currentUser = getCurrentUserUseCase()
        if (currentUser != null) {
            uiState = uiState.copy(
                userName = currentUser.displayName ?: currentUser.email?.substringBefore("@") ?: "Pengguna",
                userEmail = currentUser.email ?: "",
                userPhotoUrl = currentUser.photoUrl?.toString() ?: ""
            )
        }
    }

    private fun loadChildrenData() {
        val currentUser = getCurrentUserUseCase()
        if (currentUser == null) {
            uiState = uiState.copy(
                isLoading = false,
                error = "User tidak terautentikasi"
            )
            return
        }

        viewModelScope.launch {
            getChildrenByParentIdUseCase(currentUser.uid)
                .catch { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = error.message ?: "Gagal memuat data anak"
                    )
                }
                .collect { children ->
                    uiState = uiState.copy(
                        isLoading = false,
                        children = children,
                        selectedChild = children.firstOrNull(),
                        error = null
                    )
                }
        }
    }

    fun dismissWelcomePopup() {
        preferencesManager.setWelcomePopupShown()
        uiState = uiState.copy(showWelcomePopup = false)
    }

    fun selectChild(child: Child) {
        uiState = uiState.copy(selectedChild = child)
    }

    fun onSearch(query: String) {
        // TODO: Implement search functionality
    }
}
