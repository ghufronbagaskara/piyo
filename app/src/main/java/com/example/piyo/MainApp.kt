package com.example.piyo

import android.app.Application
import com.example.piyo.di.appModule
import com.example.piyo.di.piyoParentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin untuk debugging (optional, set Level.NONE di production)
            androidLogger(Level.ERROR)

            // Android context
            androidContext(this@MainApp)

            // Load modules
            modules(
                appModule,
                piyoParentModule
            )
        }
    }
}