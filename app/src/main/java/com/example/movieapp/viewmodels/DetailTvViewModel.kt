package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.TvShow
import com.example.movieapp.domain.usecase.MovieUseCase


class DetailTvViewModel constructor(private val useCase: MovieUseCase) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    private val isFavorite = MutableLiveData<Boolean>()

    fun setFavorite(tvShow: TvShow) {
        useCase.setTvShowFavorite(tvShow, !tvShow.isFavorite)
        isFavorite.postValue(!tvShow.isFavorite)
    }

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getFavorite(): LiveData<Boolean> = isFavorite
}