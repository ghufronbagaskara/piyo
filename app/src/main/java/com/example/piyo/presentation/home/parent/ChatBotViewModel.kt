package com.example.piyo.presentation.home.parent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piyo.data.ChatbotService
import com.example.piyo.domain.model.Child
import com.example.piyo.domain.repository.ChildRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val isLoading: Boolean = false
)

data class ChatBotUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isProcessing: Boolean = false,
    val children: List<Child> = emptyList(),
    val isLoadingChildren: Boolean = true,
    val error: String? = null
)

class ChatBotViewModel(
    private val childRepository: ChildRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ChatBotUiState(
            messages = listOf(
                ChatMessage("init_1", "Halo, kenalin namaku Piyo!", isUser = false),
                ChatMessage(
                    "init_2",
                    "Aku adalah asisten virtual di aplikasi Piyo, yang dirancang untuk membantu orang tua dalam mendukung anak-anak berkebutuhan khusus, terutama autisme.",
                    isUser = false
                ),
                ChatMessage("init_3", "Bagaimana aku bisa membantu Anda hari ini?", isUser = false)
            )
        )
    )
    val uiState: StateFlow<ChatBotUiState> = _uiState.asStateFlow()

    init {
        loadChildren()
    }

    private fun loadChildren() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _uiState.value = _uiState.value.copy(
                isLoadingChildren = false,
                error = "User not authenticated"
            )
            return
        }

        viewModelScope.launch {
            try {
                childRepository.getChildrenByParentId(userId)
                    .catch { e ->
                        Log.e("ChatBotViewModel", "Error loading children", e)
                        _uiState.value = _uiState.value.copy(
                            isLoadingChildren = false,
                            error = e.message
                        )
                    }
                    .collect { children ->
                        _uiState.value = _uiState.value.copy(
                            children = children,
                            isLoadingChildren = false
                        )
                        Log.d("ChatBotViewModel", "Loaded ${children.size} children")
                    }
            } catch (e: Exception) {
                Log.e("ChatBotViewModel", "Error in loadChildren", e)
                _uiState.value = _uiState.value.copy(
                    isLoadingChildren = false,
                    error = e.message
                )
            }
        }
    }

    fun sendMessage(text: String) {
        if (_uiState.value.isProcessing || text.isBlank()) return

        val userMessage = text.trim()
        val currentMessages = _uiState.value.messages.toMutableList()

        // Add user message
        val userMsgId = "user_${System.currentTimeMillis()}"
        currentMessages.add(ChatMessage(userMsgId, userMessage, isUser = true))

        // Add loading indicator
        val loadingId = "loading_${System.currentTimeMillis()}"
        currentMessages.add(ChatMessage(loadingId, "...", isUser = false, isLoading = true))

        _uiState.value = _uiState.value.copy(
            messages = currentMessages,
            isProcessing = true
        )

        viewModelScope.launch {
            try {
                // Generate response with children context
                val result = ChatbotService.generateResponse(
                    userMessage = userMessage,
                    children = _uiState.value.children
                )

                val updatedMessages = _uiState.value.messages.toMutableList()

                // Remove loading indicator
                val loadingIndex = updatedMessages.indexOfFirst { it.id == loadingId }
                if (loadingIndex != -1) {
                    updatedMessages.removeAt(loadingIndex)
                }

                result.fold(
                    onSuccess = { reply ->
                        // Add bot response
                        updatedMessages.add(
                            ChatMessage(
                                id = "bot_${System.currentTimeMillis()}",
                                text = reply,
                                isUser = false
                            )
                        )
                    },
                    onFailure = { error ->
                        // Add error message
                        updatedMessages.add(
                            ChatMessage(
                                id = "error_${System.currentTimeMillis()}",
                                text = "❌ ${error.message ?: "Terjadi kesalahan"}",
                                isUser = false
                            )
                        )
                        Log.e("ChatBotViewModel", "Error generating response", error)
                    }
                )

                _uiState.value = _uiState.value.copy(
                    messages = updatedMessages,
                    isProcessing = false
                )
            } catch (e: Exception) {
                val updatedMessages = _uiState.value.messages.toMutableList()

                // Remove loading indicator
                val loadingIndex = updatedMessages.indexOfFirst { it.id == loadingId }
                if (loadingIndex != -1) {
                    updatedMessages.removeAt(loadingIndex)
                }

                // Add error message
                updatedMessages.add(
                    ChatMessage(
                        id = "error_${System.currentTimeMillis()}",
                        text = "❌ Terjadi kesalahan saat memproses pesan",
                        isUser = false
                    )
                )

                _uiState.value = _uiState.value.copy(
                    messages = updatedMessages,
                    isProcessing = false
                )
                Log.e("ChatBotViewModel", "Unexpected error in sendMessage", e)
            }
        }
    }
}

