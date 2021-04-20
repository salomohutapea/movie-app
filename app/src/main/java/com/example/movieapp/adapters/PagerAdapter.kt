package com.example.movieapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.MovieTvFragment
import com.example.movieapp.models.NowPlayingMovies
import com.example.movieapp.models.OnAirTvShows

class PagerAdapter(
    activity: AppCompatActivity,
    private var nowPlayingMovies: NowPlayingMovies,
    private var tvShows: OnAirTvShows,
) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return MovieTvFragment.newInstance(position + 1, nowPlayingMovies, tvShows)
    }

    override fun getItemCount(): Int {
        return 2
    }
}