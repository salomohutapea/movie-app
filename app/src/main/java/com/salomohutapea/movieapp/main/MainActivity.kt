package com.salomohutapea.movieapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.salomohutapea.movieapp.ui.PagerAdapter
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)

        binding.switchFavorite.setOnCheckedChangeListener { _, _ ->
            displayPager()
        }

        displayPager()
    }

    private fun displayPager() {
        supportActionBar?.elevation = 0f
        pagerAdapter = PagerAdapter(this, binding.switchFavorite.isChecked)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "MOVIES"
                1 -> tab.text = "TV SHOWS"
            }
        }.attach()
    }

}