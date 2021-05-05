package com.example.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel()
        viewModel.setData()
        viewModel.isLoading.postValue(false)
    }

    @Test
    fun getAllData() {
        val dataEntities = viewModel.getAllData()
        assertNotNull(dataEntities)
        assertEquals(20, dataEntities.value.first.movies.size)
        assertEquals(20, dataEntities.value.second.onAir.size)
    }

    @Test
    fun getIsLoading() {
        val loadingEntity = viewModel.getIsLoading()
        assertNotNull(loadingEntity)
        assertEquals(false, loadingEntity.value)
    }
}