package com.example.movieapp.core.data.source.remote

import com.example.movieapp.core.domain.model.GenreEntity
import com.example.movieapp.core.domain.model.MovieEntity
import com.example.movieapp.core.domain.model.TvShowEntity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getAllMovies(): MovieEntity

    @GET("tv/on_the_air")
    suspend fun getAllTvShows(): TvShowEntity

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<GenreEntity>

    @GET("genre/tv/list")
    suspend fun getTvGenres(): Response<GenreEntity>
}