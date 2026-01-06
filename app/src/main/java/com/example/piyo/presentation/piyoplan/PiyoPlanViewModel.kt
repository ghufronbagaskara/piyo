package com.example.piyo.presentation.piyoplan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piyo.domain.model.PlanTask
import com.example.piyo.domain.usecase.auth.GetCurrentUserUseCase
import com.example.piyo.domain.usecase.plan.AddPlanTaskUseCase
import com.example.piyo.domain.usecase.plan.DeletePlanTaskUseCase
import com.example.piyo.domain.usecase.plan.ObservePlanTasksUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class PiyoPlanUiState(
    val isLoading: Boolean = true,
    val tasks: List<PlanTask> = emptyList(),
    val error: String? = null
)

class PiyoPlanViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val observePlanTasksUseCase: ObservePlanTasksUseCase,
    private val addPlanTaskUseCase: AddPlanTaskUseCase,
    private val deletePlanTaskUseCase: DeletePlanTaskUseCase
) : ViewModel() {

    var uiState by mutableStateOf(PiyoPlanUiState())
        private set

    init {
        loadTasks()
    }

    private fun loadTasks() {
        val currentUser = getCurrentUserUseCase()
        if (currentUser == null) {
            uiState = uiState.copy(
                isLoading = false,
                error = "User tidak terautentikasi"
            )
            return
        }

        viewModelScope.launch {
            observePlanTasksUseCase(currentUser.uid)
                .catch { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = error.message ?: "Gagal memuat data"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { tasks ->
                            uiState = uiState.copy(
                                isLoading = false,
                                tasks = tasks,
                                error = null
                            )
                        },
                        onFailure = { error ->
                            uiState = uiState.copy(
                                isLoading = false,
                                error = error.message ?: "Gagal memuat data"
                            )
                        }
                    )
                }
        }
    }

    fun addSampleTask() {
        val currentUser = getCurrentUserUseCase() ?: return

        viewModelScope.launch {
            val newTask = PlanTask(
                parentId = currentUser.uid,
                childId = "", // Could be selected child ID
                title = "Aktivitas Baru",
                time = "15:00 - 16:00",
                description = "Deskripsi aktivitas",
                date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    .format(java.util.Date())
            )

            addPlanTaskUseCase(newTask).fold(
                onSuccess = {
                    // Task added successfully, will be updated via Flow
                },
                onFailure = { error ->
                    uiState = uiState.copy(
                        error = error.message ?: "Gagal menambah task"
                    )
                }
            )
        }
    }

    fun deleteTask(task: PlanTask) {
        viewModelScope.launch {
            deletePlanTaskUseCase(task.id).fold(
                onSuccess = {
                    // Task deleted successfully, will be updated via Flow
                },
                onFailure = { error ->
                    uiState = uiState.copy(
                        error = error.message ?: "Gagal menghapus task"
                    )
                }
            )
        }
    }

    fun clearError() {
        uiState = uiState.copy(error = null)
    }
}
