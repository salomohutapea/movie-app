package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.data.Repository
import com.example.movieapp.data.source.local.AppDatabase
import com.example.movieapp.data.source.local.LocalDataSource
import com.example.movieapp.data.source.remote.NetworkHandler
import com.example.movieapp.data.source.remote.RemoteDataSource
import com.example.movieapp.domain.repository.IMovieRepository
import com.example.movieapp.domain.usecase.MovieInteractor
import com.example.movieapp.domain.usecase.MovieUseCase
import com.example.movieapp.utils.AppExecutors
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
object Injection {
    private fun provideRepository(context: Context): IMovieRepository {

        val database = AppDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.movieDao(), database.tvShowDao())
        val remoteRepository = RemoteDataSource.getInstance(NetworkHandler(context))
        val appExecutors = AppExecutors()

        return Repository.getInstance(remoteRepository, localDataSource, appExecutors)
    }

    fun provideMovieUseCase(context: Context): MovieUseCase {
        val repository = provideRepository(context)
        return MovieInteractor(repository)
    }
}
