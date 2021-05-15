package com.example.movieapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {

    fun getAllMovies(): LiveData<Resource<List<Movie>>>

    fun getAllTvShows(): LiveData<Resource<List<TvShow>>>

    fun getFavoriteMovies(): LiveData<List<Movie>>

    fun getFavoriteTvShows(): LiveData<List<TvShow>>

    fun setMovieFavorite(movie: Movie, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShow, state: Boolean)

    fun getMovieById(movieId: String)

    fun getTvShowById(tvShowId: String)

    fun getMovieFavoritePaging(): Flow<PagingData<Movie>>

    fun getTvShowFavoritePaging(): Flow<PagingData<TvShow>>
}