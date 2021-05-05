package com.example.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movieapp.data.Repository
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import com.example.movieapp.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val dummyMovieTv = DataDummy.generateDummyMoviesAndTv()
    private val dummyGenres = DataDummy.generateDummyMovieTvGenres()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var movieObserver: Observer<MovieEntity>

    @Mock
    private lateinit var tvObserver: Observer<TvShowEntity>

    @Mock
    private lateinit var movieGenresObserver: Observer<GenreEntity>

    @Mock
    private lateinit var tvGenresObserver: Observer<GenreEntity>

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
        viewModel.isLoading.postValue(false)
    }

    @Test
    fun getIsLoading() {
        val loadingEntity = viewModel.getIsLoading()
        assertNotNull(loadingEntity)
        assertEquals(false, loadingEntity.value)
    }

    @Test
    fun getTvShows() {
        val tvShows = MutableLiveData<TvShowEntity>()
        tvShows.value = dummyMovieTv.second

        `when`(repository.getAllTvShows()).thenReturn(tvShows)
        val tvShowEntity = viewModel.getTvShows().value as TvShowEntity
        verify(repository).getAllTvShows()
        assertNotNull(tvShowEntity)
        assertEquals(dummyMovieTv.second.tvShow?.size, tvShowEntity.tvShow?.size)
        assertEquals(dummyMovieTv.second.tvShow?.get(0)?.id, tvShowEntity.tvShow?.get(0)?.id)
        assertEquals(dummyMovieTv.second.tvShow?.get(0)?.genre, tvShowEntity.tvShow?.get(0)?.genre)
        assertEquals(dummyMovieTv.second.tvShow?.get(0)?.posterPath, tvShowEntity.tvShow?.get(0)?.posterPath)
        assertEquals(dummyMovieTv.second.tvShow?.get(0)?.originalName, tvShowEntity.tvShow?.get(0)?.originalName)
        assertEquals(dummyMovieTv.second.tvShow?.get(0)?.overview, tvShowEntity.tvShow?.get(0)?.overview)

        viewModel.getTvShows().observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyMovieTv.second)
    }

    @Test
    fun getMovies() {
        val movies = MutableLiveData<MovieEntity>()
        movies.value = dummyMovieTv.first

        `when`(repository.getAllMovies()).thenReturn(movies)
        val movieEntity = viewModel.getMovies().value as MovieEntity
        verify(repository).getAllMovies()
        assertNotNull(movieEntity)
        assertEquals(dummyMovieTv.first.movies?.size, movieEntity.movies?.size)
        assertEquals(dummyMovieTv.first.movies?.get(0)?.id, movieEntity.movies?.get(0)?.id)
        assertEquals(dummyMovieTv.first.movies?.get(0)?.genre, movieEntity.movies?.get(0)?.genre)
        assertEquals(dummyMovieTv.first.movies?.get(0)?.posterPath, movieEntity.movies?.get(0)?.posterPath)
        assertEquals(dummyMovieTv.first.movies?.get(0)?.originalTitle, movieEntity.movies?.get(0)?.originalTitle)
        assertEquals(dummyMovieTv.first.movies?.get(0)?.overview, movieEntity.movies?.get(0)?.overview)

        viewModel.getMovies().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovieTv.first)
    }

    @Test
    fun getTvGenres() {
        val tvGenres = MutableLiveData<GenreEntity>()
        tvGenres.value = dummyGenres.second

        `when`(repository.getTvGenres()).thenReturn(tvGenres)
        val tvGenreEntity = viewModel.getTvGenres().value as GenreEntity
        verify(repository).getTvGenres()
        assertNotNull(tvGenreEntity)
        assertEquals(dummyGenres.second.genres?.size, tvGenreEntity.genres?.size)
        assertEquals(dummyGenres.second.genres?.get(0)?.id, tvGenreEntity.genres?.get(0)?.id)
        assertEquals(dummyGenres.second.genres?.get(0)?.name, tvGenreEntity.genres?.get(0)?.name)

        viewModel.getTvGenres().observeForever(tvGenresObserver)
        verify(tvGenresObserver).onChanged(dummyGenres.second)
    }

    @Test
    fun getMovieGenres() {
        val movieGenres = MutableLiveData<GenreEntity>()
        movieGenres.value = dummyGenres.first

        `when`(repository.getMovieGenres()).thenReturn(movieGenres)
        val movieGenreEntity = viewModel.getMovieGenres().value as GenreEntity
        verify(repository).getMovieGenres()
        assertNotNull(movieGenreEntity)
        assertEquals(dummyGenres.first.genres?.size, movieGenreEntity.genres?.size)
        assertEquals(dummyGenres.first.genres?.get(0)?.id, movieGenreEntity.genres?.get(0)?.id)
        assertEquals(dummyGenres.first.genres?.get(0)?.name, movieGenreEntity.genres?.get(0)?.name)

        viewModel.getMovieGenres().observeForever(movieGenresObserver)
        verify(movieGenresObserver).onChanged(dummyGenres.first)
    }
}