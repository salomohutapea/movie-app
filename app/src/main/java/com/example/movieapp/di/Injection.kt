package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.data.Repository
import com.example.movieapp.data.local.LocalDataSource
import com.example.movieapp.data.local.RoomDb
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.handlers.NetworkHandler
import com.example.movieapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {

        val database = RoomDb.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.movieDao(), database.tvShowDao())
        val remoteRepository = RemoteDataSource.getInstance(NetworkHandler(context))
        val appExecutors = AppExecutors()

        return Repository.getInstance(remoteRepository, localDataSource, appExecutors)
    }
}
