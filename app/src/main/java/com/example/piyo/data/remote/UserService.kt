package com.example.piyo.data.remote

import com.example.piyo.data.dto.user.UserDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserService(private val firestore: FirebaseFirestore) {
    private val usersCollection = firestore.collection("users")

    suspend fun createUser(userId: String, data: Map<String, Any>) {
        usersCollection.document(userId).set(data).await()
    }

    suspend fun updateUser(userId: String, data: Map<String, Any>) {
        usersCollection.document(userId).update(data).await()
    }

    suspend fun getUserById(userId: String): UserDto? {
        val snapshot = usersCollection.document(userId).get().await()
        return snapshot.toObject(UserDto::class.java)
    }

    fun observeUser(userId: String): Flow<UserDto?> = callbackFlow {
        val listener = usersCollection.document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val user = snapshot?.toObject(UserDto::class.java)
                trySend(user)
            }
        awaitClose { listener.remove() }
    }

    suspend fun deleteUser(userId: String) {
        usersCollection.document(userId).delete().await()
    }
}

