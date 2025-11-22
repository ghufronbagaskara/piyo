package com.example.piyo.data.remote

import com.example.piyo.data.dto.education.EducationContentDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class EducationContentService(private val firestore: FirebaseFirestore) {

    fun observeEducationContents(): Flow<List<EducationContentDto>> = callbackFlow {
        val listener = firestore.collection("education_contents")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val data = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(EducationContentDto::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(data)
            }
        awaitClose { listener.remove() }
    }

    suspend fun searchEducationContents(query: String): List<EducationContentDto> {
        return try {
            val snapshot = firestore.collection("education_contents").get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(EducationContentDto::class.java)?.copy(id = doc.id)
            }.filter { content ->
                content.title?.contains(query, ignoreCase = true) == true ||
                content.tags?.any { it.contains(query, ignoreCase = true) } == true
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

