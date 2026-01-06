package com.example.piyo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quizId: String,
    val userId: String,
    val childId: String,
    val score: Int,
    val totalQuestions: Int,
    val completedAt: Long,
    val isSynced: Boolean = false
)

