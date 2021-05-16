package com.example.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieapp.data.Repository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {
    private lateinit var viewModel: DetailTvViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        viewModel = DetailTvViewModel(repository)
        viewModel.isLoading.postValue(false)
    }

    @Test
    fun getIsLoading() {
        val loadingEntity = viewModel.getIsLoading()
        Assert.assertNotNull(loadingEntity)
        Assert.assertEquals(false, loadingEntity.value)
    }
}