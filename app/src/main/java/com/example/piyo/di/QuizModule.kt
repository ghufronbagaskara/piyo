package com.example.piyo.di

import com.example.piyo.data.mapper.quiz.QuizMapper
import com.example.piyo.data.remote.QuizContentService
import com.example.piyo.data.remote.QuizService
import com.example.piyo.data.repository.quiz.QuizRepositoryImpl
import com.example.piyo.domain.repository.QuizRepository
import com.example.piyo.domain.usecase.quiz.GetQuizByAgeUseCase
import com.example.piyo.domain.usecase.quiz.GetRecommendedContentsUseCase
import com.example.piyo.domain.usecase.quiz.SubmitQuizResultUseCase
import com.example.piyo.presentation.quiz.QuizViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val quizModule = module {
    // Services
    single { QuizContentService(firestore = get()) }
    single { QuizService(firestore = get()) }

    // Mappers
    single { QuizMapper() }

    // Repositories
    single<QuizRepository> {
        QuizRepositoryImpl(
            contentService = get(),
            quizService = get(),
            mapper = get()
        )
    }

    // Use Cases
    factory { GetQuizByAgeUseCase(get()) }
    factory { SubmitQuizResultUseCase(get()) }
    factory { GetRecommendedContentsUseCase(get()) }

    // ViewModel
    viewModel {
        QuizViewModel(
            getQuizByAgeUseCase = get(),
            submitQuizResultUseCase = get(),
            getRecommendedContentsUseCase = get(),
            auth = get()
        )
    }
}
