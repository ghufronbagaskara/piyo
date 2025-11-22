package com.example.piyo.presentation.home.parent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piyo.domain.model.EducationContent
import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.usecase.education.ObserveEducationContentsUseCase
import com.example.piyo.domain.usecase.education.SearchEducationContentsUseCase
import com.example.piyo.domain.usecase.quiz.ObserveQuizzesUseCase
import com.example.piyo.domain.usecase.quiz.SearchQuizzesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PiyoParentViewModel(
    private val observeEducationContentsUseCase: ObserveEducationContentsUseCase,
    private val searchEducationContentsUseCase: SearchEducationContentsUseCase,
    private val observeQuizzesUseCase: ObserveQuizzesUseCase,
    private val searchQuizzesUseCase: SearchQuizzesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(PiyoParentUiState())
        private set

    private var observeEducationJob: Job? = null
    private var observeQuizJob: Job? = null

    init {
        observeEducationContents()
        observeQuizzes()
    }

    private fun observeEducationContents() {
        observeEducationJob?.cancel()
        observeEducationJob = viewModelScope.launch {
            observeEducationContentsUseCase().collect { result ->
                result.fold(
                    onSuccess = { contents ->
                        uiState = uiState.copy(
                            educationContents = contents,
                            isEducationLoading = false,
                            educationError = null
                        )
                    },
                    onFailure = { error ->
                        uiState = uiState.copy(
                            isEducationLoading = false,
                            educationError = error.message ?: "Unknown error"
                        )
                    }
                )
            }
        }
    }

    private fun observeQuizzes() {
        observeQuizJob?.cancel()
        observeQuizJob = viewModelScope.launch {
            observeQuizzesUseCase().collect { result ->
                result.fold(
                    onSuccess = { quizzes ->
                        uiState = uiState.copy(
                            quizzes = quizzes,
                            isQuizLoading = false,
                            quizError = null
                        )
                    },
                    onFailure = { error ->
                        uiState = uiState.copy(
                            isQuizLoading = false,
                            quizError = error.message ?: "Unknown error"
                        )
                    }
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        uiState = uiState.copy(searchQuery = query)
        if (query.isBlank()) {
            observeEducationContents()
            observeQuizzes()
        } else {
            searchContents(query)
        }
    }

    private fun searchContents(query: String) {
        viewModelScope.launch {
            if (uiState.selectedTabIndex == 0) {
                searchEducationContentsUseCase(query).fold(
                    onSuccess = { contents ->
                        uiState = uiState.copy(
                            educationContents = contents,
                            isEducationLoading = false
                        )
                    },
                    onFailure = { error ->
                        uiState = uiState.copy(
                            isEducationLoading = false,
                            educationError = error.message
                        )
                    }
                )
            } else {
                searchQuizzesUseCase(query).fold(
                    onSuccess = { quizzes ->
                        uiState = uiState.copy(
                            quizzes = quizzes,
                            isQuizLoading = false
                        )
                    },
                    onFailure = { error ->
                        uiState = uiState.copy(
                            isQuizLoading = false,
                            quizError = error.message
                        )
                    }
                )
            }
        }
    }

    fun onTabSelected(index: Int) {
        uiState = uiState.copy(selectedTabIndex = index)
    }

    override fun onCleared() {
        super.onCleared()
        observeEducationJob?.cancel()
        observeQuizJob?.cancel()
    }
}

data class PiyoParentUiState(
    val selectedTabIndex: Int = 0,
    val searchQuery: String = "",
    val educationContents: List<EducationContent> = emptyList(),
    val quizzes: List<Quiz> = emptyList(),
    val isEducationLoading: Boolean = true,
    val isQuizLoading: Boolean = true,
    val educationError: String? = null,
    val quizError: String? = null
)

