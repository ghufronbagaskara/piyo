package com.example.piyo.domain.model

data class QuizResult(
    val id: String = "",
    val userId: String = "",
    val childId: String = "",
    val childAge: Int = 0,
    val quizId: String = "",
    val segmentUsed: String = "",
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val answers: List<Answer> = emptyList(),
    val completedAt: Long = 0L,
    val duration: Int = 0,
    val recommendedContent: List<String> = emptyList()
)

data class Answer(
    val questionId: String = "",
    val selectedAnswer: String = "",
    val isCorrect: Boolean = false
)
