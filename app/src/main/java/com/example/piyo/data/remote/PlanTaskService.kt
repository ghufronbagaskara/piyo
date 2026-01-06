package com.example.piyo.data.remote

import com.example.piyo.data.dto.plan.PlanTaskDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PlanTaskService(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("plan_tasks")

    fun observeTasksByParentId(parentId: String): Flow<List<PlanTaskDto>> = callbackFlow {
        val listener = collection
            .whereEqualTo("parentId", parentId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val tasks = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(PlanTaskDto::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(tasks)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addTask(task: Map<String, Any>): Result<String> {
        return try {
            val docRef = collection.add(task).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTask(taskId: String, task: Map<String, Any>): Result<Unit> {
        return try {
            collection.document(taskId).update(task).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            collection.document(taskId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun toggleTaskCompletion(taskId: String, isCompleted: Boolean): Result<Unit> {
        return try {
            collection.document(taskId)
                .update(
                    mapOf(
                        "isCompleted" to isCompleted,
                        "updatedAt" to com.google.firebase.Timestamp.now()
                    )
                )
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

