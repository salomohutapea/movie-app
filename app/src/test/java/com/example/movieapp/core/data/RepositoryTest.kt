package com.example.movieapp.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.example.movieapp.core.data.source.local.LocalDataSource
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.TvShow
import com.example.movieapp.core.data.source.remote.RemoteDataSource
import com.example.movieapp.core.utils.AppExecutors
import com.example.movieapp.core.utils.DataDummy
import com.example.movieapp.core.utils.PagedListUtil
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import java.util.concurrent.Executor


class RepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)

    private val executor = Executor { it.run() }
    private val appExecutors = AppExecutors(executor)

    private val repository = FakeRepository(remote, local, appExecutors)
    private val movieTvResponse = DataDummy.generateDummyMoviesAndTv()

    @Test
    fun setMovieFavorite() {
        val dummy = DataDummy.generateDummyMoviesAndTv().first.movies?.get(0)

        if (dummy != null) {
            repository.setMovieFavorite(dummy, true)
            verify(local, times(1)).setFavoriteMovie(dummy, true)
        }
    }

    @Test
    fun setTvShowFavorite() {
        val dummy = DataDummy.generateDummyMoviesAndTv().second.tvShow?.get(0)

        if (dummy != null) {
            repository.setTvShowFavorite(dummy, true)
            verify(local, times(1)).setFavoriteTvShow(dummy, true)
        }
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
        Mockito.`when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        repository.getFavoriteMovies()

        val courseEntities =
            com.salomohutapea.movieapp.core.data.Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMoviesAndTv().first.movies as List<Movie>))
        verify(local).getFavoriteMovies()
        Assert.assertNotNull(courseEntities.data)
        assertEquals(
            movieTvResponse.first.movies?.size?.toLong(),
            courseEntities.data.size.toLong()
        )
    }

    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShow>
        Mockito.`when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        repository.getFavoriteTvShows()

        val courseEntities =
            com.salomohutapea.movieapp.core.data.Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMoviesAndTv().second.tvShow as List<TvShow>))
        verify(local).getFavoriteTvShows()
        Assert.assertNotNull(courseEntities.data)
        assertEquals(
            movieTvResponse.second.tvShow?.size?.toLong(),
            courseEntities.data.size.toLong()
        )
    }

    @Test
    fun getAllMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
        Mockito.`when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        repository.getAllMovies()

        val courseEntities =
            com.salomohutapea.movieapp.core.data.Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMoviesAndTv().first.movies as List<Movie>))
        verify(local).getAllMovies()
        Assert.assertNotNull(courseEntities.data)
        assertEquals(
            movieTvResponse.first.movies?.size?.toLong(),
            courseEntities.data.size.toLong()
        )
    }

    @Test
    fun getAllTvShows() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShow>
        Mockito.`when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        repository.getAllTvShows()

        val courseEntities =
            com.salomohutapea.movieapp.core.data.Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMoviesAndTv().second.tvShow as List<TvShow>))
        verify(local).getAllTvShows()
        Assert.assertNotNull(courseEntities.data)
        assertEquals(
            movieTvResponse.second.tvShow?.size?.toLong(),
            courseEntities.data.size.toLong()
        )
    }
}