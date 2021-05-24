package com.salomohutapea.movieapp.core.data.source.local

import com.salomohutapea.movieapp.core.data.source.local.dao.MovieDao
import com.salomohutapea.movieapp.core.data.source.local.dao.TvShowDao
import com.salomohutapea.movieapp.core.domain.model.Movie
import com.salomohutapea.movieapp.core.domain.model.TvShow

class LocalDataSource(
    private val mMovieDao: MovieDao,
    private val mTvShowDao: TvShowDao
) {

    fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        movie.isFavorite = newState
        mMovieDao.update(movie)
    }

    fun setFavoriteTvShow(tvShow: TvShow, newState: Boolean) {
        tvShow.isFavorite = newState
        mTvShowDao.update(tvShow)
    }

    suspend fun insertMovies(movies: List<Movie>) = mMovieDao.insertMovies(movies)

    suspend fun insertTvShows(tvShows: List<TvShow>) = mTvShowDao.insertTvShows(tvShows)

    fun getFavoriteMovies() = mMovieDao.getFavoriteMovies()

    fun getFavoriteTvShows() = mTvShowDao.getFavoriteTvShows()

    fun getAllMovies() = mMovieDao.getAllMovies()

    fun getAllTvShows() = mTvShowDao.getAllTvShows()
}