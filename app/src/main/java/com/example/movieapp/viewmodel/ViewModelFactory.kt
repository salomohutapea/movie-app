package com.example.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.Repository
import com.example.movieapp.di.Injection
import com.example.movieapp.viewmodels.DetailMovieViewModel
import com.example.movieapp.viewmodels.DetailTvViewModel
import com.example.movieapp.viewmodels.MainViewModel

class ViewModelFactory private constructor(private val mRepository: Repository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context)).apply { instance = this }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> {
                DetailTvViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}