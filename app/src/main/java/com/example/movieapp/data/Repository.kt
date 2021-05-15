package com.example.movieapp.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.local.LocalDataSource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.data.remote.ApiResponse
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.utils.AppExecutors
import com.example.movieapp.vo.Resource
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null

        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                Repository(remoteData, localData, appExecutors).apply { instance = this }
            }
    }

    override fun getAllMovies(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<Movie>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<Movie>> =
                localDataSource.getAllMovies()

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<Movie>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(data: List<Movie>) {

                localDataSource.insertMovies(data)
            }

        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvShow>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<TvShow>> =
                localDataSource.getAllTvShows()

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShow>>> =
                remoteDataSource.getAllTvShows()

            public override fun saveCallResult(data: List<TvShow>) {
                localDataSource.insertTvShows(data)
            }

        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> = localDataSource.getFavoriteMovies()

    override fun getFavoriteTvShows(): LiveData<List<TvShow>> = localDataSource.getFavoriteTvShows()

    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
    }

    override fun setTvShowFavorite(tvShow: TvShow, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvShow, state) }
    }

    override fun getMovieById(movieId: String) {
        localDataSource.getMovieById(movieId)
    }

    override fun getTvShowById(tvShowId: String) {
        localDataSource.getTvShowById(tvShowId)
    }

    override fun getMovieFavoritePaging(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { localDataSource.getFavoriteMoviePaging() }
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getTvShowFavoritePaging(): Flow<PagingData<TvShow>> {
        val pagingSourceFactory = { localDataSource.getFavoriteTvShowPaging() }
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

}