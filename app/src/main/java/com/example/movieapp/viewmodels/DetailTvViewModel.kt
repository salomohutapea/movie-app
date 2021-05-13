package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Repository
import com.example.movieapp.data.model.TvShow

class DetailTvViewModel(private val repository: Repository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    private val isFavorite = MutableLiveData<Boolean>()

    fun setFavorite(tvShow: TvShow) {
        repository.setTvShowFavorite(tvShow, !tvShow.isFavorite)
        isFavorite.postValue(!tvShow.isFavorite)
    }

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getFavorite(): LiveData<Boolean> = isFavorite
}