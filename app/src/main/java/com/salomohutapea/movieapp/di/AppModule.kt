package com.salomohutapea.movieapp.di

import com.salomohutapea.movieapp.core.domain.usecase.MovieInteractor
import com.salomohutapea.movieapp.core.domain.usecase.MovieUseCase
import com.salomohutapea.movieapp.moviedetail.MovieDetailViewModel
import com.salomohutapea.movieapp.movietvfragment.MovieTvViewModel
import com.salomohutapea.movieapp.tvdetail.TvDetailViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

@DelicateCoroutinesApi
val viewModelModule = module {
    viewModel { MovieDetailViewModel(get()) }
    viewModel { TvDetailViewModel(get()) }
    viewModel { MovieTvViewModel(get()) }
}