package com.example.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.salomohutapea.movieapp.core.data.Repository
import com.example.movieapp.core.domain.model.TvShow
import com.example.movieapp.tvdetail.TvDetailViewModel
import com.example.movieapp.core.utils.DataDummy
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
class TvDetailViewModelTest {
    private lateinit var viewModel: TvDetailViewModel
    private val dummyTv = DataDummy.generateDummyMoviesAndTv().second.tvShow as List<TvShow>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: com.salomohutapea.movieapp.core.data.Repository

    @Mock
    private lateinit var observer: Observer<Boolean>

    @Before
    fun setUp() {
        viewModel = TvDetailViewModel(repository)
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