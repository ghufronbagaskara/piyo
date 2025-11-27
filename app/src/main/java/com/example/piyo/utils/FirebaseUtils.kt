package com.example.piyo.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await

object FirebaseUtils {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    suspend fun registerUser(email: String, password: String, fullName: String = ""): FirebaseUser? {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user

        // Create user document in Firestore
        user?.let {
            val userData = hashMapOf(
                "email" to email,
                "fullName" to fullName,
                "photoUrl" to "",
                "role" to "parent",
                "createdAt" to Timestamp.now(),
                "updatedAt" to Timestamp.now()
            )
            firestore.collection("users").document(it.uid).set(userData).await()
        }

        return user
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
}
