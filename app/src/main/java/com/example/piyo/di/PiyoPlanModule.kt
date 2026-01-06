package com.example.piyo.di

import com.example.piyo.data.mapper.plan.PlanTaskMapper
import com.example.piyo.data.remote.PlanTaskService
import com.example.piyo.data.repository.plan.PlanTaskRepositoryImpl
import com.example.piyo.domain.repository.PlanTaskRepository
import com.example.piyo.domain.usecase.plan.AddPlanTaskUseCase
import com.example.piyo.domain.usecase.plan.DeletePlanTaskUseCase
import com.example.piyo.domain.usecase.plan.ObservePlanTasksUseCase
import com.example.piyo.presentation.piyoplan.PiyoPlanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val piyoPlanModule = module {
    // Service
    single { PlanTaskService(firestore = get()) }

    // Mapper
    single { PlanTaskMapper() }

    // Repository
    single<PlanTaskRepository> {
        PlanTaskRepositoryImpl(
            service = get(),
            mapper = get()
        )
    }

    // Use Cases
    factory { ObservePlanTasksUseCase(get()) }
    factory { AddPlanTaskUseCase(get()) }
    factory { DeletePlanTaskUseCase(get()) }

    // ViewModel
    viewModel {
        PiyoPlanViewModel(
            getCurrentUserUseCase = get(),
            observePlanTasksUseCase = get(),
            addPlanTaskUseCase = get(),
            deletePlanTaskUseCase = get()
        )
    }
}
