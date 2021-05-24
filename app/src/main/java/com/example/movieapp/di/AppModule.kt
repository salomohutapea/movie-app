package com.example.movieapp.di

import com.example.movieapp.core.domain.usecase.MovieInteractor
import com.example.movieapp.core.domain.usecase.MovieUseCase
import com.example.movieapp.moviedetail.MovieDetailViewModel
import com.example.movieapp.movietvfragment.MovieTvViewModel
import com.example.movieapp.tvdetail.TvDetailViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> {MovieInteractor(get())}
}

@DelicateCoroutinesApi
val viewModelModule = module {
    viewModel { MovieDetailViewModel(get()) }
    viewModel { TvDetailViewModel(get()) }
    viewModel { MovieTvViewModel(get())}
}