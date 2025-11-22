package com.example.piyo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val appModule = module {

    // Firebase instances (Singleton)
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseDatabase.getInstance() }

    // Retrofit (jika nanti butuh REST API)
    // single { provideRetrofit() }

    // Room Database (jika nanti butuh local DB)
    // single { provideDatabase(androidContext()) }

    // Gemini API (jika butuh)
    // single { provideGeminiClient() }
}

// Helper functions (optional)
/*
private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://your-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "piyo_database"
    ).build()
}

private fun provideGeminiClient(): GenerativeModel {
    return GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
}
*/