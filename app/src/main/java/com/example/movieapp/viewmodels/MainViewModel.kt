package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()

    fun setIsLoading(loading: Boolean) {
        isLoading.postValue(loading)
    }

    fun getIsLoading(): LiveData<Boolean> = isLoading

}