package com.example.piyo.data.local.dao

import androidx.room.*
import com.example.piyo.data.local.entity.ChildEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {

    @Query("SELECT * FROM children WHERE parentId = :parentId ORDER BY createdAt DESC")
    fun getChildrenByParentId(parentId: String): Flow<List<ChildEntity>>

    @Query("SELECT * FROM children WHERE id = :childId")
    suspend fun getChildById(childId: String): ChildEntity?

    @Query("SELECT * FROM children WHERE id = :childId")
    fun observeChildById(childId: String): Flow<ChildEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: ChildEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildren(children: List<ChildEntity>)

    @Update
    suspend fun updateChild(child: ChildEntity)

    @Delete
    suspend fun deleteChild(child: ChildEntity)

    @Query("DELETE FROM children WHERE id = :childId")
    suspend fun deleteChildById(childId: String)

    @Query("SELECT * FROM children WHERE isSynced = 0")
    suspend fun getUnsyncedChildren(): List<ChildEntity>

    @Query("UPDATE children SET isSynced = 1 WHERE id = :childId")
    suspend fun markAsSynced(childId: String)

    @Query("DELETE FROM children")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM children WHERE parentId = :parentId")
    suspend fun getChildCount(parentId: String): Int
}

