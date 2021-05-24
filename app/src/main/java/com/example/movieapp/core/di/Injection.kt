package com.example.movieapp.core.di

import android.content.Context
import com.example.movieapp.core.data.Repository
import com.example.movieapp.core.data.source.local.AppDatabase
import com.example.movieapp.core.data.source.local.LocalDataSource
import com.example.movieapp.core.data.source.remote.ApiConfig
import com.example.movieapp.core.data.source.remote.RemoteDataSource
import com.example.movieapp.core.domain.repository.IMovieRepository
import com.example.movieapp.core.domain.usecase.MovieInteractor
import com.example.movieapp.core.domain.usecase.MovieUseCase
import com.example.movieapp.core.utils.AppExecutors
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
object Injection {
    private fun provideRepository(context: Context): IMovieRepository {

        val database = AppDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.movieDao(), database.tvShowDao())
        val remoteRepository = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val appExecutors = AppExecutors()

        return Repository.getInstance(remoteRepository, localDataSource, appExecutors)
    }

    fun provideMovieUseCase(context: Context): MovieUseCase {
        val repository = provideRepository(context)
        return MovieInteractor(repository)
    }
}
