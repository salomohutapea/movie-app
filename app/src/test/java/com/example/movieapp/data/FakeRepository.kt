package com.example.movieapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import com.example.movieapp.data.remote.RemoteDataSource

class FakeRepository(private val remoteDataSource: RemoteDataSource) : MovieDataSource {

    override fun getAllMovies(): LiveData<MovieEntity> {
        val moviesResult = MutableLiveData<MovieEntity>()
        remoteDataSource.getAllMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onAllMoviesReceived(moviesResponse: MovieEntity) {
                moviesResult.postValue(moviesResponse)
            }
        })
        return moviesResult
    }

    override fun getAllTvShows(): LiveData<TvShowEntity> {
        val tvResult = MutableLiveData<TvShowEntity>()
        remoteDataSource.getAllTvShows(object : RemoteDataSource.LoadTvShowsCallback {
            override fun onAllTvShowsReceived(tvShowsResponse: TvShowEntity) {
                tvResult.postValue(tvShowsResponse)
            }
        })
        return tvResult
    }

    override fun getMovieGenres(): LiveData<GenreEntity> {
        val genreMovieResult = MutableLiveData<GenreEntity>()
        remoteDataSource.getMovieGenres(object : RemoteDataSource.LoadMovieGenresCallback {
            override fun onMovieGenresReceived(movieGenresResponse: GenreEntity) {
                genreMovieResult.postValue(movieGenresResponse)
            }
        })
        return genreMovieResult
    }

    override fun getTvGenres(): LiveData<GenreEntity> {
        val genreTvResult = MutableLiveData<GenreEntity>()
        remoteDataSource.getTvGenres(object : RemoteDataSource.LoadTvGenresCallback {
            override fun onTvGenresReceived(tvGenresResponse: GenreEntity) {
                genreTvResult.postValue(tvGenresResponse)
            }
        })
        return genreTvResult
    }

}