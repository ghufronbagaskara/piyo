package com.example.piyo.data.remote

import com.example.piyo.data.dto.userprogress.UserProgressDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserProgressService(private val firestore: FirebaseFirestore) {
    private val progressCollection = firestore.collection("user_progress")

    suspend fun saveProgress(data: Map<String, Any>): String {
        val docRef = progressCollection.document()
        docRef.set(data).await()
        return docRef.id
    }

    suspend fun updateProgress(progressId: String, data: Map<String, Any>) {
        progressCollection.document(progressId).update(data).await()
    }

    fun observeProgressByUserId(userId: String): Flow<List<UserProgressDto>> = callbackFlow {
        val listener = progressCollection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val progress = snapshot?.toObjects(UserProgressDto::class.java) ?: emptyList()
                trySend(progress)
            }
        awaitClose { listener.remove() }
    }

    fun observeProgressByChildId(childId: String): Flow<List<UserProgressDto>> = callbackFlow {
        val listener = progressCollection
            .whereEqualTo("childId", childId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val progress = snapshot?.toObjects(UserProgressDto::class.java) ?: emptyList()
                trySend(progress)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getProgressByContentId(userId: String, contentId: String): UserProgressDto? {
        val snapshot = progressCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("contentId", contentId)
            .limit(1)
            .get()
            .await()
        return snapshot.documents.firstOrNull()?.toObject(UserProgressDto::class.java)
    }
}

