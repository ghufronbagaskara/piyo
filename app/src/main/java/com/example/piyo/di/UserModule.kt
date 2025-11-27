package com.example.piyo.di

import com.example.piyo.data.mapper.quizresult.QuizResultMapper
import com.example.piyo.data.mapper.user.UserMapper
import com.example.piyo.data.mapper.userprogress.UserProgressMapper
import com.example.piyo.data.remote.QuizResultService
import com.example.piyo.data.remote.UserProgressService
import com.example.piyo.data.remote.UserService
import com.example.piyo.data.repository.quizresult.QuizResultRepositoryImpl
import com.example.piyo.data.repository.user.UserRepositoryImpl
import com.example.piyo.data.repository.userprogress.UserProgressRepositoryImpl
import com.example.piyo.domain.repository.QuizResultRepository
import com.example.piyo.domain.repository.UserProgressRepository
import com.example.piyo.domain.repository.UserRepository
import org.koin.dsl.module

val userModule = module {
    // Services
    single { UserService(firestore = get()) }
    single { QuizResultService(firestore = get()) }
    single { UserProgressService(firestore = get()) }

    // Mappers
    single { UserMapper() }
    single { QuizResultMapper() }
    single { UserProgressMapper() }

    // Repositories
    single<UserRepository> {
        UserRepositoryImpl(service = get(), mapper = get())
    }
    single<QuizResultRepository> {
        QuizResultRepositoryImpl(service = get(), mapper = get())
    }
    single<UserProgressRepository> {
        UserProgressRepositoryImpl(service = get(), mapper = get())
    }
}

