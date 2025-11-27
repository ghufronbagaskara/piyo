package com.example.piyo.data.remote

import com.example.piyo.data.dto.child.ChildDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChildService(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    private val childrenCollection = firestore.collection("children")
    private val storageRef = storage.reference

    suspend fun createChild(data: Map<String, Any>): String {
        val docRef = childrenCollection.document()
        docRef.set(data).await()
        return docRef.id
    }

    suspend fun updateChild(childId: String, data: Map<String, Any>) {
        childrenCollection.document(childId).update(data).await()
    }

    suspend fun getChildById(childId: String): ChildDto? {
        val snapshot = childrenCollection.document(childId).get().await()
        return snapshot.toObject(ChildDto::class.java)
    }

    fun observeChildrenByParentId(parentId: String): Flow<List<ChildDto>> = callbackFlow {
        val listener = childrenCollection
            .whereEqualTo("parentId", parentId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val children = snapshot?.toObjects(ChildDto::class.java) ?: emptyList()
                trySend(children)
            }
        awaitClose { listener.remove() }
    }

    suspend fun uploadProfilePhoto(childId: String, data: ByteArray): String {
        val photoRef = storageRef.child("children/$childId/profile.jpg")
        photoRef.putBytes(data).await()
        return photoRef.downloadUrl.await().toString()
    }

    suspend fun uploadBabyPhoto(childId: String, data: ByteArray): String {
        val photoRef = storageRef.child("children/$childId/birth_photo.jpg")
        photoRef.putBytes(data).await()
        return photoRef.downloadUrl.await().toString()
    }

    suspend fun deleteChild(childId: String) {
        childrenCollection.document(childId).delete().await()
    }
}

