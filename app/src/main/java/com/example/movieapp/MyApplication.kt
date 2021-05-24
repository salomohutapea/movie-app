package com.example.movieapp

import android.app.Application
import com.example.movieapp.core.di.databaseModule
import com.example.movieapp.core.di.networkModule
import com.example.movieapp.core.di.repositoryModule
import com.example.movieapp.di.useCaseModule
import com.example.movieapp.di.viewModelModule
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@DelicateCoroutinesApi
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}