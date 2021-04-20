package com.example.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel()
        viewModel.isLoading.postValue(false)
    }

    @Test
    fun getIsLoading() {
        val loadingEntity = viewModel.getIsLoading()
        Assert.assertNotNull(loadingEntity)
        Assert.assertEquals(false, loadingEntity.value)
    }
}