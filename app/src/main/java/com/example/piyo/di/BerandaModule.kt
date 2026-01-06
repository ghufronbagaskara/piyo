package com.example.piyo.di

import com.example.piyo.domain.usecase.auth.GetCurrentUserUseCase
import com.example.piyo.presentation.home.beranda.BerandaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val berandaModule = module {
    // Use Cases
    factory { GetCurrentUserUseCase(auth = get()) }

    // ViewModel
    viewModel {
        BerandaViewModel(
            getCurrentUserUseCase = get(),
            getChildrenByParentIdUseCase = get(),
            preferencesManager = get()
        )
    }
}
