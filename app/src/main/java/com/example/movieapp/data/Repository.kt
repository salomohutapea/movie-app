package com.example.movieapp.data

import androidx.lifecycle.LiveData
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

}