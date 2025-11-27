package com.example.piyo.data.remote

import com.example.piyo.data.dto.quizresult.QuizResultDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizResultService(private val firestore: FirebaseFirestore) {
    private val resultsCollection = firestore.collection("quiz_results")

    suspend fun saveQuizResult(data: Map<String, Any>): String {
        val docRef = resultsCollection.document()
        docRef.set(data).await()
        return docRef.id
    }

    fun observeQuizResultsByUserId(userId: String): Flow<List<QuizResultDto>> = callbackFlow {
        val listener = resultsCollection
            .whereEqualTo("userId", userId)
            .orderBy("completedAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val results = snapshot?.toObjects(QuizResultDto::class.java) ?: emptyList()
                trySend(results)
            }
        awaitClose { listener.remove() }
    }

    fun observeQuizResultsByChildId(childId: String): Flow<List<QuizResultDto>> = callbackFlow {
        val listener = resultsCollection
            .whereEqualTo("childId", childId)
            .orderBy("completedAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val results = snapshot?.toObjects(QuizResultDto::class.java) ?: emptyList()
                trySend(results)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getQuizResultById(resultId: String): QuizResultDto? {
        val snapshot = resultsCollection.document(resultId).get().await()
        return snapshot.toObject(QuizResultDto::class.java)
    }

    suspend fun getLatestQuizResult(userId: String): QuizResultDto? {
        val snapshot = resultsCollection
            .whereEqualTo("userId", userId)
            .orderBy("completedAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return snapshot.documents.firstOrNull()?.toObject(QuizResultDto::class.java)
    }
}
