package com.example.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movieapp.data.Repository
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.moviedetail.MovieDetailViewModel
import com.example.movieapp.utils.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {
    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovie = DataDummy.generateDummyMoviesAndTv().first.movies as List<Movie>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Boolean>

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(repository)
        viewModel.isLoading.postValue(false)
        viewModel.setFavorite(dummyMovie[0])
    }

    @Test
    fun getIsLoading() {
        val loadingEntity = viewModel.getIsLoading()
        Assert.assertNotNull(loadingEntity)
        Assert.assertEquals(false, loadingEntity.value)
    }

    @Test
    fun setFavorite() {
        val expected = MutableLiveData<Boolean>()
        expected.value = true

        viewModel.setFavorite(dummyMovie[0])
        viewModel.getFavorite().observeForever(observer)

        Mockito.verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavorite().value

        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getFavorite() {
        val getFavoriteEntity = viewModel.getFavorite()
        Assert.assertEquals(true, getFavoriteEntity.value)
    }
}