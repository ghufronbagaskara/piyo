package com.example.piyo.di

import com.example.piyo.data.mapper.education.EducationContentMapper
import com.example.piyo.data.mapper.quiz.QuizMapper
import com.example.piyo.data.remote.EducationContentService
import com.example.piyo.data.remote.QuizContentService
import com.example.piyo.data.remote.QuizService
import com.example.piyo.data.repository.education.EducationContentRepositoryImpl
import com.example.piyo.data.repository.quiz.QuizRepositoryImpl
import com.example.piyo.domain.repository.EducationContentRepository
import com.example.piyo.domain.repository.QuizRepository
import com.example.piyo.domain.usecase.education.ObserveEducationContentsUseCase
import com.example.piyo.domain.usecase.education.SearchEducationContentsUseCase
import com.example.piyo.domain.usecase.quiz.ObserveQuizzesUseCase
import com.example.piyo.domain.usecase.quiz.SearchQuizzesUseCase
import com.example.piyo.presentation.home.parent.PiyoParentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val piyoParentModule = module {
    // Remote Services
    single { EducationContentService(firestore = get()) }
    single { QuizService(firestore = get()) }
    single { QuizContentService(firestore = get()) }

    // Mappers
    single { EducationContentMapper() }
    single { QuizMapper() }

    // Repositories
    single<EducationContentRepository> {
        EducationContentRepositoryImpl(
            service = get(),
            mapper = get()
        )
    }
    single<QuizRepository> {
        QuizRepositoryImpl(
            contentService = get(),
            quizService = get(),
            mapper = get()
        )
    }

    // Use Cases - Education
    factory { ObserveEducationContentsUseCase(get()) }
    factory { SearchEducationContentsUseCase(get()) }

    // Use Cases - Quiz
    factory { ObserveQuizzesUseCase(get()) }
    factory { SearchQuizzesUseCase(get()) }

    // ViewModel
    viewModel {
        PiyoParentViewModel(
            observeEducationContentsUseCase = get(),
            searchEducationContentsUseCase = get(),
            observeQuizzesUseCase = get(),
            searchQuizzesUseCase = get()
        )
    }
}
