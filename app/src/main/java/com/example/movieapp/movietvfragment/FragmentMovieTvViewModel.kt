package com.example.movieapp.movietvfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.movieapp.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class FragmentMovieTvViewModel constructor(private val useCase: MovieUseCase) : ViewModel() {

    fun getFavoriteMovies() = useCase.getFavoriteMovies().asLiveData()

    fun getFavoriteTvShows() = useCase.getFavoriteTvShows().asLiveData()

    fun getAllMovies() = useCase.getAllMovies().asLiveData()

    fun getAllTvShows() = useCase.getAllTvShows().asLiveData()

}