package com.example.movieapp.data.local

import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.dao.TvShowDao
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.utils.EspressoIdlingResource

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

    fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        EspressoIdlingResource.increment()
        movie.isFavorite = newState
        mMovieDao.update(movie)
        EspressoIdlingResource.decrement()
    }

    fun setFavoriteTvShow(tvShow: TvShow, newState: Boolean) {
        EspressoIdlingResource.increment()
        tvShow.isFavorite = newState
        mTvShowDao.update(tvShow)
        EspressoIdlingResource.decrement()
    }

    fun insertMovies(movies: List<Movie>) = mMovieDao.insertMovies(movies)

    fun insertTvShows(tvShows: List<TvShow>) = mTvShowDao.insertTvShows(tvShows)

    fun getFavoriteMovies() = mMovieDao.getFavoriteMovies()

    fun getFavoriteTvShows() = mTvShowDao.getFavoriteTvShows()

    fun getAllMovies() = mMovieDao.getAllMovies()

    fun getAllTvShows() = mTvShowDao.getAllTvShows()
}