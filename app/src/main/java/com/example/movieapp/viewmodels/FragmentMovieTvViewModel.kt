package com.example.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Repository

class FragmentMovieTvViewModel constructor(private val repository: Repository) : ViewModel() {

    fun getFavoriteMovies() = repository.getFavoriteMovies()

    fun getFavoriteTvShows() = repository.getFavoriteTvShows()

    fun getAllMovies() = repository.getAllMovies()

    fun getAllTvShows() = repository.getAllTvShows()

}