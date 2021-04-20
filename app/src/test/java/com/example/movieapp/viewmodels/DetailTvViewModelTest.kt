package com.example.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailTvViewModelTest {
    private lateinit var viewModel: DetailTvViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = DetailTvViewModel()
        viewModel.isLoading.postValue(false)
    }

    @Test
    fun getIsLoading() {
        val loadingEntity = viewModel.getIsLoading()
        Assert.assertNotNull(loadingEntity)
        Assert.assertEquals(false, loadingEntity.value)
    }
}