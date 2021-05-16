package com.example.movieapp.data.local

import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.dao.TvShowDao
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow

class LocalDataSource private constructor(
    private val mMovieDao: MovieDao,
    private val mTvShowDao: TvShowDao
) {
    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao, tvShowDao: TvShowDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao, tvShowDao).apply {
                INSTANCE = this
            }
    }

    fun getMovieById(movieId: String) =
        mMovieDao.getMovieById(movieId)

    fun getTvShowById(tvShowId: String) =
        mMovieDao.getMovieById(tvShowId)

    fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        movie.isFavorite = newState
        mMovieDao.update(movie)
    }

    fun setFavoriteTvShow(tvShow: TvShow, newState: Boolean) {
        tvShow.isFavorite = newState
        mTvShowDao.update(tvShow)
    }

    fun insertMovies(movies: List<Movie>) = mMovieDao.insertMovies(movies)

    fun insertTvShows(tvShows: List<TvShow>) = mTvShowDao.insertTvShows(tvShows)

    fun getFavoriteMovies() = mMovieDao.getFavoriteMovies()

    fun getFavoriteTvShows() = mTvShowDao.getFavoriteTvShows()

    fun getAllMovies() = mMovieDao.getAllMovies()

    fun getAllTvShows() = mTvShowDao.getAllTvShows()
}