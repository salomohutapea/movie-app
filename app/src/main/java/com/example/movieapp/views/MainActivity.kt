package com.example.movieapp.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.adapters.PagerAdapter
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.viewmodels.MainViewModel
import com.example.movieapp.viewmodels.ViewModelFactory
import com.example.movieapp.vo.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var movieEntity: List<Movie>
    private lateinit var tvShowEntity: List<TvShow>

    private var notFavFlag = 0
    private var favFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)

        initializeViewModel()
        initializeFavSwitch()
    }

    override fun onResume() {
        super.onResume()
        favFlag = 0
        notFavFlag = 0
    }

    private fun initializeViewModel() {

        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

    }

    private fun initializeFavSwitch() {
        binding.switchFavorite.setOnCheckedChangeListener { _, isChecked ->
            favFlag = 0
            notFavFlag = 0
            mainViewModel.setIsLoading(true)
            if (isChecked) {
                favFlag = 0
                mainViewModel.favMovies().observe(this) {
                    if (it != null) {
                        favFlag++
                        movieEntity = it
                        displayPager()
                    }
                }
                mainViewModel.favTvShows().observe(this) {
                    if (it != null) {
                        favFlag++
                        tvShowEntity = it
                        displayPager()
                    }
                }
            } else {
                mainViewModel.movies().observe(this) { movies ->
                    if (movies != null) {
                        when (movies.status) {
                            Status.LOADING -> Log.d("STATUS_GETDATA", "LOADING GET MOVIES")
                            Status.SUCCESS -> {
                                Log.d("STATUS_GETDATA", "SUCCESS GET MOVIES")
                                if (movies.data != null) {
                                    movieEntity = movies.data
                                    notFavFlag++
                                    displayPager()
                                }
                                mainViewModel.setIsLoading(false)
                            }
                            Status.ERROR -> {
                                mainViewModel.setIsLoading(false)
                                Toast.makeText(
                                    applicationContext,
                                    "Terjadi kesalahan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
                mainViewModel.tvShows().observe(this) { tvShows ->
                    if (tvShows != null) {
                        when (tvShows.status) {
                            Status.LOADING -> Log.d("STATUS_GETDATA", "LOADING GET TVSHOWS")
                            Status.SUCCESS -> {
                                Log.d("STATUS_GETDATA", "SUCCESS GET TV SHOWS")
                                if (tvShows.data != null) {
                                    tvShowEntity = tvShows.data
                                    notFavFlag++
                                    displayPager()
                                }
                                mainViewModel.setIsLoading(false)
                            }
                            Status.ERROR -> {
                                mainViewModel.setIsLoading(false)
                                Toast.makeText(
                                    applicationContext,
                                    "Terjadi kesalahan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            }
        }
        binding.switchFavorite.isChecked = true
        binding.switchFavorite.isChecked = false
    }

    private fun displayPager() {
        if (notFavFlag == 2 || favFlag == 2) {
            supportActionBar?.elevation = 0f
            pagerAdapter = PagerAdapter(this, movieEntity, tvShowEntity)
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "MOVIES"
                    1 -> tab.text = "TV SHOWS"
                }
            }.attach()
            mainViewModel.setIsLoading(false)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}