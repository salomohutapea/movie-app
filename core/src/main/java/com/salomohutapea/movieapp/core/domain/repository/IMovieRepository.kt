package com.salomohutapea.movieapp.core.domain.repository

import com.salomohutapea.movieapp.core.data.Resource
import com.salomohutapea.movieapp.core.domain.model.Movie
import com.salomohutapea.movieapp.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun setMovieFavorite(movie: Movie, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShow, state: Boolean)

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getFavoriteTvShows(): Flow<List<TvShow>>

    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getAllTvShows(): Flow<Resource<List<TvShow>>>

}