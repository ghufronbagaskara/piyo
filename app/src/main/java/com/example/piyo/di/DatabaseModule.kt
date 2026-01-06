package com.example.piyo.di

import android.content.Context
import androidx.room.Room
import com.example.piyo.data.local.PiyoDatabase
import com.example.piyo.data.local.dao.ChildDao
import com.example.piyo.data.local.dao.EducationContentDao
import com.example.piyo.data.local.dao.QuizResultDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            PiyoDatabase::class.java,
            PiyoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // For development, remove in production
            .build()
    }

    // DAOs
    single<ChildDao> { get<PiyoDatabase>().childDao() }
    single<QuizResultDao> { get<PiyoDatabase>().quizResultDao() }
    single<EducationContentDao> { get<PiyoDatabase>().educationContentDao() }
}

