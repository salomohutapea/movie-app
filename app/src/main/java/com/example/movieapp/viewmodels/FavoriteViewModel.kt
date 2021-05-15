package com.example.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.movieapp.data.Repository

@ExperimentalPagingApi
class FavoriteViewModel constructor(private val repository: Repository) : ViewModel() {
    fun fetchFavoriteMovies() = repository.getMovieFavoritePaging().cachedIn(viewModelScope)
    fun fetchFavoriteTvShows() = repository.getTvShowFavoritePaging().cachedIn(viewModelScope)
}