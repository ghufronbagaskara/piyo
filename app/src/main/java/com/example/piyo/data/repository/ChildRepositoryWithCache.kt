package com.example.piyo.data.repository

import com.example.piyo.data.local.dao.ChildDao
import com.example.piyo.data.local.entity.toEntity
import com.example.piyo.data.local.entity.toDomain
import com.example.piyo.domain.model.Child
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/**
 * Repository with offline-first strategy using Room + Firebase
 */
class ChildRepositoryWithCache(
    private val childDao: ChildDao,
    private val firestore: FirebaseFirestore
) {

    /**
     * Get children with offline-first approach
     * 1. Return local data immediately
     * 2. Sync with Firebase in background
     */
    fun getChildrenByParentId(parentId: String): Flow<List<Child>> {
        // Return local data as Flow
        return childDao.getChildrenByParentId(parentId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    /**
     * Sync children from Firebase to local database
     */
    suspend fun syncChildrenFromFirebase(parentId: String): Result<Unit> {
        return try {
            val snapshot = firestore.collection("children")
                .whereEqualTo("parentId", parentId)
                .get()
                .await()

            val children = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Child::class.java)?.copy(id = doc.id)
            }

            // Save to local database
            val entities = children.map { it.toEntity(isSynced = true) }
            childDao.insertChildren(entities)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get child by ID (offline-first)
     */
    suspend fun getChildById(childId: String): Result<Child?> {
        return try {
            // Try local first
            val localChild = childDao.getChildById(childId)
            if (localChild != null) {
                return Result.success(localChild.toDomain())
            }

            // If not found locally, fetch from Firebase
            val doc = firestore.collection("children")
                .document(childId)
                .get()
                .await()

            val child = doc.toObject(Child::class.java)?.copy(id = doc.id)

            // Cache to local database
            child?.let {
                childDao.insertChild(it.toEntity(isSynced = true))
            }

            Result.success(child)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create or update child (save locally first, sync to Firebase)
     */
    suspend fun createOrUpdateChild(child: Child): Result<String> {
        return try {
            // Save to local database first
            val entity = child.toEntity(isSynced = false)
            childDao.insertChild(entity)

            // Then sync to Firebase
            val childData = hashMapOf(
                "parentId" to child.parentId,
                "fullName" to child.fullName,
                "birthDate" to child.birthDate,
                "gender" to child.gender,
                "birthWeight" to child.birthWeight,
                "diagnosisHistory" to child.diagnosisHistory,
                "profilePhotoUrl" to child.profilePhotoUrl,
                "babyPhotoUrl" to child.babyPhotoUrl,
                "updatedAt" to System.currentTimeMillis()
            )

            val docRef = if (child.id.isNotEmpty() && !child.id.startsWith("temp_")) {
                firestore.collection("children").document(child.id)
            } else {
                firestore.collection("children").document()
            }

            docRef.set(childData).await()

            // Mark as synced in local database
            childDao.markAsSynced(docRef.id)

            Result.success(docRef.id)
        } catch (e: Exception) {
            // Even if Firebase fails, data is saved locally
            Result.success(child.id)
        }
    }

    /**
     * Delete child (local + Firebase)
     */
    suspend fun deleteChild(childId: String): Result<Unit> {
        return try {
            // Delete from local first
            childDao.deleteChildById(childId)

            // Delete from Firebase
            firestore.collection("children")
                .document(childId)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sync unsynced children to Firebase
     */
    suspend fun syncUnsyncedChildren(): Result<Unit> {
        return try {
            val unsyncedChildren = childDao.getUnsyncedChildren()

            unsyncedChildren.forEach { entity ->
                val child = entity.toDomain()
                val childData = hashMapOf(
                    "parentId" to child.parentId,
                    "fullName" to child.fullName,
                    "birthDate" to child.birthDate,
                    "gender" to child.gender,
                    "birthWeight" to child.birthWeight,
                    "diagnosisHistory" to child.diagnosisHistory,
                    "profilePhotoUrl" to child.profilePhotoUrl,
                    "babyPhotoUrl" to child.babyPhotoUrl
                )

                val docRef = firestore.collection("children").document()
                docRef.set(childData).await()

                childDao.markAsSynced(entity.id)
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Check if user has child (check local first)
     */
    suspend fun hasChild(parentId: String): Boolean {
        return try {
            val localCount = childDao.getChildCount(parentId)
            if (localCount > 0) return true

            // If no local data, check Firebase
            val snapshot = firestore.collection("children")
                .whereEqualTo("parentId", parentId)
                .limit(1)
                .get()
                .await()

            !snapshot.isEmpty
        } catch (e: Exception) {
            false
        }
    }
}

