package com.example.movieapp.viewmodels

import androidx.lifecycle.ViewModel

import com.example.movieapp.data.Repository


class FragmentMovieTvViewModel constructor(private val repository: Repository) : ViewModel() {
    fun fetchFavoriteMovies() = repository.getMoviesFavoritePaging()
    fun fetchFavoriteTvShows() = repository.getTvShowsFavoritePaging()
    fun fetchMovies() = repository.getMoviesPaging()
    fun fetchTvShows() = repository.getTvShowsPaging()
}