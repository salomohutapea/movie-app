package com.salomohutapea.movieapp.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.salomohutapea.movieapp.core.domain.usecase.MovieUseCase

class FavoriteViewModel(private val useCase: MovieUseCase) : ViewModel() {

    val isLoading = MutableLiveData<Int>()

    fun getFavoriteMovies() = useCase.getFavoriteMovies().asLiveData()

    fun getFavoriteTvShows() = useCase.getFavoriteTvShows().asLiveData()
}