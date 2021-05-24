package com.example.movieapp.core.data.source.local

import com.example.movieapp.core.data.source.local.dao.MovieDao
import com.example.movieapp.core.data.source.local.dao.TvShowDao
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.TvShow

class LocalDataSource(
    private val mMovieDao: MovieDao,
    private val mTvShowDao: TvShowDao
) {
    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

    }

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