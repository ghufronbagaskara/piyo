package com.example.piyo.data.remote

import com.example.piyo.data.dto.quiz.QuizDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizService(private val firestore: FirebaseFirestore) {

    fun observeQuizzes(): Flow<List<QuizDto>> = callbackFlow {
        val listener = firestore.collection("quizzes")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val data = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(QuizDto::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(data)
            }
        awaitClose { listener.remove() }
    }

    suspend fun searchQuizzes(query: String): List<QuizDto> {
        return try {
            val snapshot = firestore.collection("quizzes").get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(QuizDto::class.java)?.copy(id = doc.id)
            }.filter { quiz ->
                quiz.title?.contains(query, ignoreCase = true) == true ||
                quiz.tags?.any { it.contains(query, ignoreCase = true) } == true
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

