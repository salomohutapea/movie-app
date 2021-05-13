package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()

    fun setIsLoading(loading: Boolean) {
        isLoading.postValue(loading)
    }

    fun tvShows() = repository.getAllTvShows()

    fun favTvShows() = repository.getFavoriteTvShows()

    fun movies() = repository.getAllMovies()

    fun favMovies() = repository.getFavoriteMovies()

    fun getIsLoading(): LiveData<Boolean> = isLoading


}