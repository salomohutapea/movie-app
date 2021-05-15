package com.example.movieapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.views.MovieTvFragment


class PagerAdapter(
    activity: AppCompatActivity,
    private var activityMainBinding: ActivityMainBinding
) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return MovieTvFragment.newInstance(position + 1, activityMainBinding)
    }

    override fun getItemCount(): Int {
        return 2
    }
}