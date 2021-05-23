package com.example.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movieapp.data.Repository
import com.example.movieapp.domain.model.TvShow
import com.example.movieapp.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {
    private lateinit var viewModel: DetailTvViewModel
    private val dummyTv = DataDummy.generateDummyMoviesAndTv().second.tvShow as List<TvShow>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Boolean>

    @Before
    fun setUp() {
        viewModel = DetailTvViewModel(repository)
        viewModel.isLoading.postValue(false)
        viewModel.setFavorite(dummyTv[0])
    }

    @Test
    fun getIsLoading() {
        val loadingEntity = viewModel.getIsLoading()
        assertNotNull(loadingEntity)
        assertEquals(false, loadingEntity.value)
    }

    @Test
    fun setFavorite() {
        val expected = MutableLiveData<Boolean>()
        expected.value = true

        viewModel.setFavorite(dummyTv[0])
        viewModel.getFavorite().observeForever(observer)

        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavorite().value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getFavorite() {
        val getFavoriteEntity = viewModel.getFavorite()
        assertEquals(true, getFavoriteEntity.value)
    }
}