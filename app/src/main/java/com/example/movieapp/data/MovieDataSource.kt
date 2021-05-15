package com.example.movieapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.vo.Resource

interface MovieDataSource {

    fun setMovieFavorite(movie: Movie, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShow, state: Boolean)

    fun getMovieById(movieId: String)

    fun getTvShowById(tvShowId: String)

    fun getFavoriteMovies(): LiveData<PagedList<Movie>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>>

    fun getAllMovies(): LiveData<Resource<PagedList<Movie>>>

    fun getAllTvShows(): LiveData<Resource<PagedList<TvShow>>>
}