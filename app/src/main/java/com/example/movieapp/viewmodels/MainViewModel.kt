package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Repository
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getTvShows(): LiveData<TvShowEntity> = repository.getAllTvShows()
    fun getMovies(): LiveData<MovieEntity> = repository.getAllMovies()
    fun getTvGenres(): LiveData<GenreEntity> = repository.getTvGenres()
    fun getMovieGenres(): LiveData<GenreEntity> = repository.getMovieGenres()

    val isLoading = MutableLiveData<Boolean>()

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}