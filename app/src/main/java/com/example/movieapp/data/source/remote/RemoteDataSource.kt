package com.example.movieapp.data.source.remote

import com.example.movieapp.domain.model.GenreEntity
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.TvShow
import com.example.movieapp.utils.EspressoIdlingResource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@DelicateCoroutinesApi
class RemoteDataSource private constructor(private val networkHandler: NetworkHandler) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(networkHandler: NetworkHandler): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(networkHandler).apply { instance = this }
            }
    }

    suspend fun getAllMovies(): Flow<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = networkHandler.getService().getAllMovies()
                lateinit var data: List<Movie>
                if (response.movies?.isNotEmpty() == true) {
                    try {
                        val genreResponse =
                            networkHandler.getService().getMovieGenres().body()
                        response.let {
                            data = addGenreToMovie(genreResponse, it.movies as List<Movie>)
                            emit(ApiResponse.Success(data))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
            EspressoIdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllTvShows(): Flow<ApiResponse<List<TvShow>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = networkHandler.getService().getAllTvShows()
                lateinit var data: List<TvShow>
                if (response.tvShow?.isNotEmpty() == true) {
                    try {
                        val genreResponse =
                            networkHandler.getService().getTvGenres().body()
                        response.let {
                            data = addGenreToTvShows(genreResponse, it.tvShow as List<TvShow>)
                            emit(ApiResponse.Success(data))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
            EspressoIdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
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