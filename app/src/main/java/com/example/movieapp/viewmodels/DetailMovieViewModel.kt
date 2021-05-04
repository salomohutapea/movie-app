package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Repository

class DetailMovieViewModel(private val repository: Repository) : ViewModel() {

    //TODO: GET MOVIE DETAIL BY ID
    val isLoading = MutableLiveData<Boolean>()

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}