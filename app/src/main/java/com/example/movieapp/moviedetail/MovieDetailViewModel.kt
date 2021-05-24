package com.example.movieapp.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.usecase.MovieUseCase


class MovieDetailViewModel constructor(private val useCase: MovieUseCase) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    private val isFavorite = MutableLiveData<Boolean>()

    fun setFavorite(movie: Movie) {
        useCase.setMovieFavorite(movie, !movie.isFavorite)
        isFavorite.postValue(!movie.isFavorite)
    }

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getFavorite(): LiveData<Boolean> = isFavorite
}