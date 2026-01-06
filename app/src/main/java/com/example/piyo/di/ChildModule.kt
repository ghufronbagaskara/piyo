package com.example.piyo.di

import com.example.piyo.data.mapper.child.ChildMapper
import com.example.piyo.data.remote.ChildService
import com.example.piyo.data.repository.child.ChildRepositoryImpl
import com.example.piyo.data.repository.ChildRepositoryWithCache
import com.example.piyo.domain.repository.ChildRepository
import com.example.piyo.domain.usecase.child.*
import com.example.piyo.presentation.infoanak.ChildInfoViewModel
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val childModule = module {
    // Firebase Storage
    single { FirebaseStorage.getInstance() }

    // Remote Service
    single { ChildService(firestore = get(), storage = get()) }

    // Mapper
    single { ChildMapper() }

    // Repository
    single<ChildRepository> {
        ChildRepositoryImpl(
            service = get(),
            mapper = get()
        )
    }

    // Repository with Cache (Room + Firebase)
    single {
        ChildRepositoryWithCache(
            childDao = get(),
            firestore = get()
        )
    }

    // Use Cases
    factory { CreateOrUpdateChildUseCase(get()) }
    factory { GetChildByIdUseCase(get()) }
    factory { UploadChildProfilePhotoUseCase(get()) }
    factory { UploadBabyPhotoUseCase(get()) }
    factory { GetChildrenByParentIdUseCase(get()) }
    factory { CheckUserHasChildUseCase(get()) }

    // Use Cases with Cache
    factory { GetChildrenWithCacheUseCase(get()) }
    factory { SyncChildrenUseCase(get()) }

    // ViewModel
    viewModel {
        ChildInfoViewModel(
            createOrUpdateChildUseCase = get(),
            uploadChildProfilePhotoUseCase = get(),
            uploadBabyPhotoUseCase = get(),
            getChildByIdUseCase = get(),
            auth = get()
        )
    }
}
