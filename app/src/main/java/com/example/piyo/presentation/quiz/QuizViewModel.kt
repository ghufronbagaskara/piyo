package com.example.piyo.presentation.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piyo.domain.model.Answer
import com.example.piyo.domain.model.EducationContent
import com.example.piyo.domain.model.Question
import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.model.QuizResult
import com.example.piyo.domain.usecase.quiz.GetQuizByAgeUseCase
import com.example.piyo.domain.usecase.quiz.GetRecommendedContentsUseCase
import com.example.piyo.domain.usecase.quiz.SubmitQuizResultUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class QuizState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val quiz: Quiz? = null,
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false,
    val correctAnswerCount: Int = 0,
    val userAnswers: List<Answer> = emptyList(),
    val timeRemaining: Int = 900, // 15 minutes in seconds
    val isQuizCompleted: Boolean = false,
    val quizResult: QuizResult? = null,
    val recommendedContents: List<EducationContent> = emptyList(),
    val isSubmitting: Boolean = false
)

class QuizViewModel(
    private val getQuizByAgeUseCase: GetQuizByAgeUseCase,
    private val submitQuizResultUseCase: SubmitQuizResultUseCase,
    private val getRecommendedContentsUseCase: GetRecommendedContentsUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    var state by mutableStateOf(QuizState())
        private set

    private var timerJob: Job? = null
    private var childAge: Int = 0
    private var childId: String = ""

    fun loadQuiz(age: Int, selectedChildId: String) {
        childAge = age
        childId = selectedChildId

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            getQuizByAgeUseCase(age).fold(
                onSuccess = { quiz ->
                    state = state.copy(
                        isLoading = false,
                        quiz = quiz,
                        timeRemaining = quiz.duration
                    )
                },
                onFailure = { error ->
                    state = state.copy(
                        isLoading = false,
                        error = error.message ?: "Gagal memuat kuis"
                    )
                }
            )
        }
    }

    fun startQuiz() {
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (state.timeRemaining > 0 && !state.isQuizCompleted) {
                delay(1000)
                state = state.copy(timeRemaining = state.timeRemaining - 1)
            }
            if (state.timeRemaining == 0 && !state.isQuizCompleted) {
                autoSubmitQuiz()
            }
        }
    }

    fun selectAnswer(answerIndex: Int) {
        if (state.isAnswered) return

        val currentQuestion = getCurrentQuestion() ?: return
        val isCorrect = answerIndex == currentQuestion.correctAnswerIndex

        state = state.copy(
            selectedAnswerIndex = answerIndex,
            isAnswered = true,
            isCorrect = isCorrect,
            correctAnswerCount = if (isCorrect) state.correctAnswerCount + 1 else state.correctAnswerCount
        )

        // Save answer
        val answer = Answer(
            questionId = state.currentQuestionIndex.toString(),
            selectedAnswer = currentQuestion.options.getOrNull(answerIndex) ?: "",
            isCorrect = isCorrect
        )
        state = state.copy(userAnswers = state.userAnswers + answer)
    }

    fun nextQuestion() {
        val quiz = state.quiz ?: return

        if (state.currentQuestionIndex < quiz.questions.size - 1) {
            state = state.copy(
                currentQuestionIndex = state.currentQuestionIndex + 1,
                selectedAnswerIndex = null,
                isAnswered = false,
                isCorrect = false
            )
        } else {
            completeQuiz()
        }
    }

    private fun completeQuiz() {
        timerJob?.cancel()
        state = state.copy(isQuizCompleted = true)
        submitQuizResult()
        loadRecommendedContents()
    }

    private fun autoSubmitQuiz() {
        completeQuiz()
    }

    private fun submitQuizResult() {
        val quiz = state.quiz ?: return
        val currentUser = auth.currentUser ?: return

        viewModelScope.launch {
            state = state.copy(isSubmitting = true)

            val totalQuestions = quiz.questions.size
            val correctAnswers = state.correctAnswerCount
            val score = (correctAnswers * 100) / totalQuestions

            val result = QuizResult(
                userId = currentUser.uid,
                childId = childId,
                childAge = childAge,
                quizId = quiz.id,
                segmentUsed = quiz.ageSegment,
                score = score,
                totalQuestions = totalQuestions,
                correctAnswers = correctAnswers,
                answers = state.userAnswers,
                completedAt = System.currentTimeMillis() / 1000,
                duration = quiz.duration - state.timeRemaining,
                recommendedContent = emptyList() // Will be updated after fetching recommendations
            )

            submitQuizResultUseCase(result).fold(
                onSuccess = { resultId ->
                    state = state.copy(
                        isSubmitting = false,
                        quizResult = result.copy(id = resultId)
                    )
                },
                onFailure = { error ->
                    state = state.copy(
                        isSubmitting = false,
                        error = error.message
                    )
                }
            )
        }
    }

    private fun loadRecommendedContents() {
        val quiz = state.quiz ?: return

        viewModelScope.launch {
            getRecommendedContentsUseCase(quiz.ageSegment).firstOrNull()?.let { contents ->
                state = state.copy(recommendedContents = contents)
            }
        }
    }

    fun getCurrentQuestion(): Question? {
        return state.quiz?.questions?.getOrNull(state.currentQuestionIndex)
    }

    fun getProgress(): Float {
        val quiz = state.quiz ?: return 0f
        return (state.currentQuestionIndex + 1).toFloat() / quiz.questions.size.toFloat()
    }

    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }

    fun clearError() {
        state = state.copy(error = null)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

