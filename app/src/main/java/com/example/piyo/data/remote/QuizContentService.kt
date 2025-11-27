package com.example.piyo.data.remote

import com.example.piyo.data.dto.quiz.ParentingQuizDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizContentService(private val firestore: FirebaseFirestore) {
    private val quizzesCollection = firestore.collection("quizzes")

    suspend fun getQuizByAgeSegment(ageSegment: String): ParentingQuizDto? {
        val snapshot = quizzesCollection
            .whereEqualTo("ageSegment", ageSegment)
            .limit(1)
            .get()
            .await()

        return snapshot.documents.firstOrNull()?.toObject(ParentingQuizDto::class.java)
    }

    fun observeAllQuizzes(): Flow<List<ParentingQuizDto>> = callbackFlow {
        val listener = quizzesCollection
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val quizzes = snapshot?.toObjects(ParentingQuizDto::class.java) ?: emptyList()
                trySend(quizzes)
            }
        awaitClose { listener.remove() }
    }
}
