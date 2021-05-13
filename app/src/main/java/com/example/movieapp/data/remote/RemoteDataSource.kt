package com.example.movieapp.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.model.*
import com.example.movieapp.handlers.NetworkHandler
import com.example.movieapp.utils.EspressoIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    fun getAllMovies(): LiveData<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<Movie>>>()
        networkHandler.getService().getAllMovies().enqueue(object :
            Callback<MovieEntity> {

            override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                Log.d("Requestt Failed", "Get movies, ${t.cause}")
            }

            override fun onResponse(
                call: Call<MovieEntity>,
                response: Response<MovieEntity>
            ) {
                lateinit var data: List<Movie>
                try {
                    GlobalScope.launch {
                        val genreResponse =
                            networkHandler.getService().getMovieGenres().body()
                        response.body()?.let {
                            data = addGenreToMovie(genreResponse, it.movies as List<Movie>)
                            resultMovie.postValue(ApiResponse.success(data))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getAllTvShows(): LiveData<ApiResponse<List<TvShow>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShow>>>()
        networkHandler.getService().getAllTvShows().enqueue(object :
            Callback<TvShowEntity> {

            override fun onFailure(call: Call<TvShowEntity>, t: Throwable) {
                Log.d("Requestt Failed", "Get tv shows, ${t.cause}")
            }

            override fun onResponse(
                call: Call<TvShowEntity>,
                response: Response<TvShowEntity>
            ) {
                lateinit var data: List<TvShow>
                try {
                    GlobalScope.launch {
                        val genreResponse =
                            networkHandler.getService().getTvGenres().body()
                        response.body()?.let {
                            data = addGenreToTvShows(genreResponse, it.tvShow as List<TvShow>)
                            resultTvShow.postValue(ApiResponse.success(data))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                EspressoIdlingResource.decrement()
            }
        })
        return resultTvShow
    }

    private fun addGenreToMovie(movieGenres: GenreEntity?, movieEntity: List<Movie>): List<Movie> {
        movieEntity.forEach { movie ->
            movie.genres = ArrayList()
            movie.genreIds?.forEach { id ->
                movieGenres?.genres?.forEach {
                    if (id == it.id.toString()) {
                        it.name?.let { genreName -> movie.genres?.add(genreName) }
                    }
                }
            }
        }
        return movieEntity
    }

    private fun addGenreToTvShows(
        tvGenres: GenreEntity?,
        tvShowEntity: List<TvShow>
    ): List<TvShow> {
        tvShowEntity.forEach { tvShows ->
            tvShows.genres = ArrayList()
            tvShows.genreIds?.forEach { id ->
                tvGenres?.genres?.forEach { it ->
                    if (id == it.id.toString()) {
                        it.name?.let { genreName -> tvShows.genres?.add(genreName) }
                    }
                }
            }
        }
        return tvShowEntity
    }

}
