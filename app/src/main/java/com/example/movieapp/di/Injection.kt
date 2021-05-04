package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.data.Repository
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.handlers.NetworkHandler

object Injection {
    fun provideRepository(context: Context): Repository {

        val remoteRepository = RemoteDataSource.getInstance(NetworkHandler(context))

        return Repository.getInstance(remoteRepository)
    }
}
