package com.example.movieapp.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.adapters.PagerAdapter
import com.example.movieapp.data.model.GenreEntity
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.viewmodel.ViewModelFactory
import com.example.movieapp.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout
    private lateinit var pagerAdapter: PagerAdapter

    private var movieEntity = MovieEntity()
    private var tvShowEntity = TvShowEntity()
    private var movieGenres = GenreEntity()
    private var tvGenres = GenreEntity()

    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)

        initializeViewModel()
    }

    private fun initializeViewModel() {
        flag = 0

        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

        mainViewModel.isLoading.postValue(true)

        mainViewModel.getMovies().observe(this) {
            flag++
            movieEntity = it
            addGenreToData()
        }
        mainViewModel.getTvShows().observe(this) {
            flag++
            tvShowEntity = it
            addGenreToData()
        }
        mainViewModel.getMovieGenres().observe(this) {
            flag++
            movieGenres = it
            addGenreToData()
        }
        mainViewModel.getTvGenres().observe(this) {
            flag++
            tvGenres = it
            addGenreToData()
        }
    }

    private fun displayPager(data: Pair<MovieEntity, TvShowEntity>) {
        supportActionBar?.elevation = 0f
        pagerAdapter = PagerAdapter(this, data.first, data.second)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "MOVIES"
                1 -> tab.text = "TV SHOWS"
            }
        }.attach()
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun addGenreToData() {
        if (flag == 4) {
            movieEntity.movies?.forEach { movie ->
                movie.genre = ArrayList()
                movie.genre_ids?.forEach { id ->
                    movieGenres.genres?.forEach {
                        if (id == it.id) {
                            it.name?.let { genre_name -> movie.genre?.add(genre_name) }
                        }
                    }
                }
            }
            tvShowEntity.onAir?.forEach { tvShows ->
                tvShows.genre = ArrayList()
                tvShows.genre_ids?.forEach { id ->
                    tvGenres.genres?.forEach { it ->
                        if (id == it.id) {
                            it.name?.let { genre_name -> tvShows.genre?.add(genre_name) }
                        }
                    }
                }
            }
            displayPager(Pair(movieEntity, tvShowEntity))
            mainViewModel.isLoading.postValue(false)
        }
    }
}