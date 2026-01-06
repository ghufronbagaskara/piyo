package com.example.piyo.data.local.dao

import androidx.room.*
import com.example.piyo.data.local.entity.EducationContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EducationContentDao {

    @Query("SELECT * FROM education_contents ORDER BY createdAt DESC")
    fun getAllEducationContents(): Flow<List<EducationContentEntity>>

    @Query("SELECT * FROM education_contents WHERE category = :category ORDER BY createdAt DESC")
    fun getContentsByCategory(category: String): Flow<List<EducationContentEntity>>

    @Query("SELECT * FROM education_contents WHERE id = :contentId")
    suspend fun getContentById(contentId: String): EducationContentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: EducationContentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(contents: List<EducationContentEntity>)

    @Query("DELETE FROM education_contents WHERE cachedAt < :timestamp")
    suspend fun deleteOldCache(timestamp: Long)

    @Query("DELETE FROM education_contents")
    suspend fun deleteAll()

    @Query("SELECT * FROM education_contents WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchContents(query: String): Flow<List<EducationContentEntity>>
}

