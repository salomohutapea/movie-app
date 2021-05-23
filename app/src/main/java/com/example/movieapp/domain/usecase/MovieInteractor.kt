package com.example.movieapp.domain.usecase

import com.example.movieapp.data.Resource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.TvShow
import com.example.movieapp.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val repository: IMovieRepository) : MovieUseCase {
    override fun setMovieFavorite(movie: Movie, state: Boolean) =
        repository.setMovieFavorite(movie, state)

    override fun setTvShowFavorite(tvShow: TvShow, state: Boolean) =
        repository.setTvShowFavorite(tvShow, state)

    override fun getFavoriteMovies(): Flow<List<Movie>> = repository.getFavoriteMovies()

    override fun getFavoriteTvShows(): Flow<List<TvShow>> = repository.getFavoriteTvShows()

    override fun getAllMovies(): Flow<Resource<List<Movie>>> = repository.getAllMovies()

    override fun getAllTvShows(): Flow<Resource<List<TvShow>>> = repository.getAllTvShows()
}