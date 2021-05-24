package com.example.movieapp.core.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.core.data.source.local.LocalDataSource
import com.example.movieapp.core.data.source.remote.ApiResponse
import com.example.movieapp.core.data.source.remote.RemoteDataSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.TvShow
import com.example.movieapp.core.domain.repository.IMovieRepository
import com.example.movieapp.core.utils.AppExecutors

class FakeRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

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

    override fun getAllMovies(): LiveData<com.salomohutapea.movieapp.core.data.Resource<PagedList<Movie>>> {
        return object : com.salomohutapea.movieapp.core.data.NetworkBoundResource<PagedList<Movie>, List<Movie>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<Movie>> {
                return LivePagedListBuilder(
                    localDataSource.getAllMovies(),
                    getDefaultPageConfig()
                ).build()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty()

            public override suspend fun createCall(): LiveData<ApiResponse<List<Movie>>> {
                return remoteDataSource.getAllMovies()
            }

            public override suspend fun saveCallResult(data: List<Movie>) {
                localDataSource.insertMovies(data)
            }

        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<com.salomohutapea.movieapp.core.data.Resource<PagedList<TvShow>>> {
        return object : com.salomohutapea.movieapp.core.data.NetworkBoundResource<PagedList<TvShow>, List<TvShow>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvShow>> {
                return LivePagedListBuilder(
                    localDataSource.getAllTvShows(),
                    getDefaultPageConfig()
                ).build()
            }

            override fun shouldFetch(data: PagedList<TvShow>?): Boolean =
                data == null || data.isEmpty()

            public override suspend fun createCall(): LiveData<ApiResponse<List<TvShow>>> =
                remoteDataSource.getAllTvShows()

            public override suspend fun saveCallResult(data: List<TvShow>) {
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