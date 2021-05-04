package com.example.movieapp.data

import androidx.lifecycle.LiveData
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity

interface MovieDataSource {

    fun getAllMovies(): LiveData<MovieEntity>

    fun getAllTvShows(): LiveData<TvShowEntity>

    fun getMovieGenres(): LiveData<GenreEntity>

    fun getTvGenres(): LiveData<GenreEntity>
}