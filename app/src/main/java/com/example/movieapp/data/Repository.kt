package com.example.movieapp.data

import com.example.movieapp.data.source.local.LocalDataSource
import com.example.movieapp.data.source.remote.ApiResponse
import com.example.movieapp.data.source.remote.RemoteDataSource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.TvShow
import com.example.movieapp.domain.repository.IMovieRepository
import com.example.movieapp.utils.AppExecutors
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow

@DelicateCoroutinesApi
class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                Repository(remoteData, localData, appExecutors).apply { instance = this }
            }
    }

    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
    }

    override fun setTvShowFavorite(tvShow: TvShow, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvShow, state) }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies()
    }

    override fun getFavoriteTvShows(): Flow<List<TvShow>> {
        return localDataSource.getFavoriteTvShows()
    }

    override fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<Movie>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies()
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<Movie>>> {
                return remoteDataSource.getAllMovies()
            }

            override suspend fun saveCallResult(data: List<Movie>) {
                localDataSource.insertMovies(data)
            }

        }.asFlow()
    }

    override fun getAllTvShows(): Flow<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvShow>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTvShows()
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvShow>>> =
                remoteDataSource.getAllTvShows()

            override suspend fun saveCallResult(data: List<TvShow>) {
                localDataSource.insertTvShows(data)
            }
        }.asFlow()
    }
}