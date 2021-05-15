package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.movieapp.data.Repository
import com.example.movieapp.data.model.Movie

@ExperimentalPagingApi
class DetailMovieViewModel constructor(private val repository: Repository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    private val isFavorite = MutableLiveData<Boolean>()

    fun setFavorite(movie: Movie) {
        repository.setMovieFavorite(movie, !movie.isFavorite)
        isFavorite.postValue(!movie.isFavorite)
    }

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getFavorite(): LiveData<Boolean> = isFavorite
}