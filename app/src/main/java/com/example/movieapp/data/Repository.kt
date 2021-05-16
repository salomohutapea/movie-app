package com.example.movieapp.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.data.local.LocalDataSource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.data.remote.ApiResponse
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.utils.AppExecutors
import com.example.movieapp.vo.Resource


class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

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

    override fun getFavoriteMovies(): LiveData<PagedList<Movie>> {
        return LivePagedListBuilder(
            localDataSource.getFavoriteMovies(),
            getDefaultPageConfig()
        ).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> {
        return LivePagedListBuilder(
            localDataSource.getFavoriteTvShows(),
            getDefaultPageConfig()
        ).build()
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<Movie>>> {
        return object : NetworkBoundResource<PagedList<Movie>, List<Movie>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<Movie>> {
                return LivePagedListBuilder(
                    localDataSource.getAllMovies(),
                    getDefaultPageConfig()
                ).build()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<Movie>>> {
                return remoteDataSource.getAllMovies()
            }

            public override fun saveCallResult(data: List<Movie>) {
                localDataSource.insertMovies(data)
            }

        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShow>>> {
        return object : NetworkBoundResource<PagedList<TvShow>, List<TvShow>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvShow>> {
                return LivePagedListBuilder(
                    localDataSource.getAllTvShows(),
                    getDefaultPageConfig()
                ).build()
            }

            override fun shouldFetch(data: PagedList<TvShow>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShow>>> =
                remoteDataSource.getAllTvShows()

            public override fun saveCallResult(data: List<TvShow>) {
                localDataSource.insertTvShows(data)
            }
        }.asLiveData()
    }

    private fun getDefaultPageConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
    }

}