package com.example.piyo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.piyo.data.local.dao.ChildDao
import com.example.piyo.data.local.dao.EducationContentDao
import com.example.piyo.data.local.dao.QuizResultDao
import com.example.piyo.data.local.entity.ChildEntity
import com.example.piyo.data.local.entity.EducationContentEntity
import com.example.piyo.data.local.entity.QuizResultEntity

@Database(
    entities = [
        ChildEntity::class,
        QuizResultEntity::class,
        EducationContentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PiyoDatabase : RoomDatabase() {

    abstract fun childDao(): ChildDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun educationContentDao(): EducationContentDao

    companion object {
        const val DATABASE_NAME = "piyo_database"
    }
}

