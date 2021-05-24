package com.salomohutapea.movieapp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.salomohutapea.movieapp.core.domain.model.Movie
import com.salomohutapea.movieapp.core.domain.model.TvShow
import com.salomohutapea.movieapp.core.ui.FavoriteMovieAdapter
import com.salomohutapea.movieapp.core.ui.FavoriteTvShowAdapter
import com.salomohutapea.movieapp.favorite.databinding.ActivityFavoriteBinding
import com.salomohutapea.movieapp.moviedetail.MovieDetailActivity
import com.salomohutapea.movieapp.tvdetail.TvDetailActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@DelicateCoroutinesApi
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
            if (it != null) {
                insertDataMovie(it)
            }
            viewModel.isLoading.value = viewModel.isLoading.value?.plus(1)
        })
        viewModel.getFavoriteTvShows().observe(this, {
            if (it != null) {
                insertDataTvShow(it)
            }
            viewModel.isLoading.value = viewModel.isLoading.value?.plus(1)
        })
        viewModel.isLoading.observe(this, {
            if (it == 2) {
                showLoading(false)
            }
        })
    }

    private fun insertDataMovie(list: List<Movie>) {
        val _adapter = FavoriteMovieAdapter()
        _adapter.setData(list)
        with(binding.favRvMovie) {
            adapter = _adapter
            itemAnimator = null
            setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(this@FavoriteActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }

        _adapter.setOnItemClickCallback(object : FavoriteMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val intent = Intent(applicationContext, MovieDetailActivity::class.java)
                intent.putExtra("MOVIE", data)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })
    }

    private fun insertDataTvShow(list: List<TvShow>) {
        val _adapter = FavoriteTvShowAdapter()
        _adapter.setData(list)

        with(binding.favRvTv) {
            adapter = _adapter
            itemAnimator = null
            setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(this@FavoriteActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }

        _adapter.setOnItemClickCallback(object : FavoriteTvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                val intent = Intent(applicationContext, TvDetailActivity::class.java)
                intent.putExtra("TVSHOW", data)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
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