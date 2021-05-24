package com.salomohutapea.movieapp

import android.app.Application
import com.salomohutapea.movieapp.core.di.databaseModule
import com.salomohutapea.movieapp.core.di.networkModule
import com.salomohutapea.movieapp.core.di.repositoryModule
import com.salomohutapea.movieapp.di.useCaseModule
import com.salomohutapea.movieapp.di.viewModelModule
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