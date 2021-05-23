package com.example.movieapp.domain.repository

import com.example.movieapp.data.Resource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun setMovieFavorite(movie: Movie, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShow, state: Boolean)

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getFavoriteTvShows(): Flow<List<TvShow>>

    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getAllTvShows(): Flow<Resource<List<TvShow>>>

}