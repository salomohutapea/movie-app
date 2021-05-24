package com.example.movieapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.ui.movietvfragment.MovieTvFragment


class PagerAdapter(
    activity: AppCompatActivity,
    private var isShowingFavorite: Boolean
) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return MovieTvFragment.newInstance(position + 1, isShowingFavorite)
    }

    override fun getItemCount(): Int {
        return 2
    }
}