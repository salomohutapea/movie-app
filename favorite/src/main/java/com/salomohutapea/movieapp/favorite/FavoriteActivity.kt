package com.salomohutapea.movieapp.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.salomohutapea.movieapp.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        supportActionBar?.hide()

        observeData()
    }

    private fun observeData() {
        showLoading(true)
        viewModel.isLoading.postValue(0)
        viewModel.getFavoriteMovies().observe(this, {
            if(it != null) {
                viewModel.isLoading.value = viewModel.isLoading.value?.plus(1)
            }
        })
        viewModel.getFavoriteTvShows().observe(this, {
            viewModel.isLoading.value = viewModel.isLoading.value?.plus(1)
        })
        viewModel.isLoading.observe(this, {
            if(it == 2) {
                showLoading(false)
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressFavorite.visibility = View.VISIBLE
        } else {
            binding.progressFavorite.visibility = View.GONE
        }
    }
}