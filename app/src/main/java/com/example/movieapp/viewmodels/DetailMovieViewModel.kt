package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailMovieViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()


    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}