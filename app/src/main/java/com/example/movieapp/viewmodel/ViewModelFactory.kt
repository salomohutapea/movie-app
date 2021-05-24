package com.example.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.di.Injection
import com.example.movieapp.domain.usecase.MovieUseCase
import com.example.movieapp.ui.main.MainViewModel
import com.example.movieapp.ui.moviedetail.MovieDetailViewModel
import com.example.movieapp.ui.movietvfragment.FragmentMovieTvViewModel
import com.example.movieapp.ui.tvdetail.TvDetailViewModel
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class ViewModelFactory private constructor(private val movieUseCase: MovieUseCase) : ViewModelProvider.NewInstanceFactory() {

    @DelicateCoroutinesApi
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideMovieUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(TvDetailViewModel::class.java) -> {
                TvDetailViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel() as T
            }
            modelClass.isAssignableFrom(FragmentMovieTvViewModel::class.java) -> {
                FragmentMovieTvViewModel(movieUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}