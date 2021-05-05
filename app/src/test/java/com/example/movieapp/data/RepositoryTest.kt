package com.example.movieapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.LiveDataTestUtil
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.verify

class RepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val fakeRepository = FakeRepository(remote)
    private val dummyMovieTv = DataDummy.generateDummyMoviesAndTv()
    private val dummyGenres = DataDummy.generateDummyMovieTvGenres()

    @Test
    fun getAllMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMoviesCallback)
                .onAllMoviesReceived(dummyMovieTv.first)
            null
        }.`when`(remote).getAllMovies(any())
        val movieEntities = LiveDataTestUtil.getValue(fakeRepository.getAllMovies())
        verify(remote).getAllMovies(any())
        Assert.assertNotNull(movieEntities)
        assertEquals(
            dummyMovieTv.first.movies?.size?.toLong(),
            movieEntities.movies?.size?.toLong()
        )
    }

    @Test
    fun getAllTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowsCallback)
                .onAllTvShowsReceived(dummyMovieTv.second)
            null
        }.`when`(remote).getAllTvShows(any())
        val tvShowEntities = LiveDataTestUtil.getValue(fakeRepository.getAllTvShows())
        verify(remote).getAllTvShows(any())
        Assert.assertNotNull(tvShowEntities)
        assertEquals(
            dummyMovieTv.first.movies?.size?.toLong(),
            tvShowEntities.tvShow?.size?.toLong()
        )
    }

    @Test
    fun getMovieGenres() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieGenresCallback)
                .onMovieGenresReceived(dummyGenres.first)
            null
        }.`when`(remote).getMovieGenres(any())
        val movieGenresEntities = LiveDataTestUtil.getValue(fakeRepository.getMovieGenres())
        verify(remote).getMovieGenres(any())
        Assert.assertNotNull(movieGenresEntities)
        assertEquals(
            dummyGenres.first.genres?.size?.toLong(),
            movieGenresEntities.genres?.size?.toLong()
        )
    }

    @Test
    fun getTvGenres() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvGenresCallback)
                .onTvGenresReceived(dummyGenres.second)
            null
        }.`when`(remote).getTvGenres(any())
        val tvGenresEntities = LiveDataTestUtil.getValue(fakeRepository.getTvGenres())
        verify(remote).getTvGenres(any())
        Assert.assertNotNull(tvGenresEntities)
        assertEquals(
            dummyGenres.second.genres?.size?.toLong(),
            tvGenresEntities.genres?.size?.toLong()
        )
    }
}