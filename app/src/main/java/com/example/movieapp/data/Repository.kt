package com.example.movieapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.data.remote.RemoteDataSource.*

class Repository private constructor(private val remoteDataSource: RemoteDataSource) :
    MovieDataSource {
    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(remoteData: RemoteDataSource): Repository =
            instance ?: synchronized(this) {
                Repository(remoteData).apply { instance = this }
            }
    }

    override fun getAllMovies(): LiveData<MovieEntity> {
        val moviesResult = MutableLiveData<MovieEntity>()
        remoteDataSource.getAllMovies(object : LoadMoviesCallback {
            override fun onAllMoviesReceived(moviesResponse: MovieEntity) {
                moviesResult.postValue(moviesResponse)
            }
        })
        return moviesResult
    }

    override fun getAllTvShows(): LiveData<TvShowEntity> {
        val tvResult = MutableLiveData<TvShowEntity>()
        remoteDataSource.getAllTvShows(object : LoadTvShowsCallback {
            override fun onAllTvShowsReceived(tvShowsResponse: TvShowEntity) {
                tvResult.postValue(tvShowsResponse)
            }
        })
        return tvResult
    }

    override fun getMovieGenres(): LiveData<GenreEntity> {
        val genreMovieResult = MutableLiveData<GenreEntity>()
        remoteDataSource.getMovieGenres(object : LoadMovieGenresCallback {
            override fun onMovieGenresReceived(movieGenresResponse: GenreEntity) {
                genreMovieResult.postValue(movieGenresResponse)
            }
        })
        return genreMovieResult
    }

    override fun getTvGenres(): LiveData<GenreEntity> {
        val genreTvResult = MutableLiveData<GenreEntity>()
        remoteDataSource.getTvGenres(object : LoadTvGenresCallback {
            override fun onTvGenresReceived(tvGenresResponse: GenreEntity) {
                genreTvResult.postValue(tvGenresResponse)
            }
        })
        return genreTvResult
    }

}