package com.salomohutapea.movieapp.core.data.source.remote

import com.salomohutapea.movieapp.core.domain.model.GenreEntity
import com.salomohutapea.movieapp.core.domain.model.Movie
import com.salomohutapea.movieapp.core.domain.model.TvShow
import com.salomohutapea.movieapp.core.utils.EspressoIdlingResource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

@DelicateCoroutinesApi
class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovies(): Flow<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getAllMovies()
                lateinit var data: List<Movie>
                if (response.movies?.isNotEmpty() == true) {
                    try {
                        val genreResponse =
                            apiService.getMovieGenres().body()
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
                val response = apiService.getAllTvShows()
                lateinit var data: List<TvShow>
                if (response.tvShow?.isNotEmpty() == true) {
                    try {
                        val genreResponse =
                            apiService.getTvGenres().body()
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
