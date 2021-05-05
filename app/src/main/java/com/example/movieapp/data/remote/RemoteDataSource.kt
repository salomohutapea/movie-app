package com.example.movieapp.data.remote

import android.util.Log
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import com.example.movieapp.handlers.NetworkHandler
import com.example.movieapp.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val networkHandler: NetworkHandler) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(networkHandler: NetworkHandler): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(networkHandler).apply { instance = this }
            }
    }

    fun getAllMovies(callback: LoadMoviesCallback) {
        EspressoIdlingResource.increment()
        networkHandler.getService().getAllMovies().enqueue(object :
            Callback<MovieEntity> {

            override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                Log.d("Request Failed", "Search user")
            }

            override fun onResponse(
                call: Call<MovieEntity>,
                response: Response<MovieEntity>
            ) {
                response.body()?.let { callback.onAllMoviesReceived(it) }
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getAllTvShows(callback: LoadTvShowsCallback) {
        EspressoIdlingResource.increment()
        networkHandler.getService().getAllTvShows().enqueue(object :
            Callback<TvShowEntity> {

            override fun onFailure(call: Call<TvShowEntity>, t: Throwable) {
                Log.d("Request Failed", "Search user")
            }

            override fun onResponse(
                call: Call<TvShowEntity>,
                response: Response<TvShowEntity>
            ) {
                response.body()?.let { callback.onAllTvShowsReceived(it) }
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getMovieGenres(callback: LoadMovieGenresCallback) {
        EspressoIdlingResource.increment()
        networkHandler.getService().getMovieGenres().enqueue(object :
            Callback<GenreEntity> {

            override fun onFailure(call: Call<GenreEntity>, t: Throwable) {
                Log.d("Request Failed", "Search user")
            }

            override fun onResponse(
                call: Call<GenreEntity>,
                response: Response<GenreEntity>
            ) {
                response.body()?.let { callback.onMovieGenresReceived(it) }
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getTvGenres(callback: LoadTvGenresCallback) {
        EspressoIdlingResource.increment()
        networkHandler.getService().getTvGenres().enqueue(object :
            Callback<GenreEntity> {

            override fun onFailure(call: Call<GenreEntity>, t: Throwable) {
                Log.d("Request Failed", "Search user")
            }

            override fun onResponse(
                call: Call<GenreEntity>,
                response: Response<GenreEntity>
            ) {
                response.body()?.let { callback.onTvGenresReceived(it) }
                EspressoIdlingResource.decrement()
            }
        })
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(moviesResponse: MovieEntity)
    }

    interface LoadTvShowsCallback {
        fun onAllTvShowsReceived(tvShowsResponse: TvShowEntity)
    }

    interface LoadMovieGenresCallback {
        fun onMovieGenresReceived(movieGenresResponse: GenreEntity)
    }

    interface LoadTvGenresCallback {
        fun onTvGenresReceived(tvGenresResponse: GenreEntity)
    }
}
