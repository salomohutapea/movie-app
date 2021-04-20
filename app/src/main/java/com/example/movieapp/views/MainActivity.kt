package com.example.movieapp.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.adapters.PagerAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.models.NowPlayingMovies
import com.example.movieapp.models.OnAirTvShows
import com.example.movieapp.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout
    private lateinit var token: String
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)

        mainViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.setData()

        mainViewModel.getAllData().observe(this) {
            displayPager(it)
        }
        mainViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }
    }

    private fun displayPager(data: Pair<NowPlayingMovies, OnAirTvShows>) {
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
}