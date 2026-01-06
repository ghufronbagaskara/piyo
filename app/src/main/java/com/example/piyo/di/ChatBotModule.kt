package com.example.piyo.di

import com.example.piyo.presentation.home.parent.ChatBotViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatBotModule = module {
    // ViewModel
    viewModel {
        ChatBotViewModel(
            childRepository = get(),
            auth = get()
        )
    }
}

