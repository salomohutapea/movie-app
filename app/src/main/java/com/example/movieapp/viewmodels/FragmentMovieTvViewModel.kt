package com.example.movieapp.viewmodels

import androidx.lifecycle.ViewModel

import com.example.movieapp.data.Repository


class FragmentMovieTvViewModel constructor(private val repository: Repository) : ViewModel() {

    fun fetchFavoriteMovies() = repository.getFavoriteMovies()

    fun fetchFavoriteTvShows() = repository.getFavoriteTvShows()

    fun fetchMovies() = repository.getAllMovies()

    fun fetchTvShows() = repository.getAllTvShows()

}