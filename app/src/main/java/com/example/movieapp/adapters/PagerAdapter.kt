package com.example.movieapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import com.example.movieapp.views.MovieTvFragment

class PagerAdapter(
    activity: AppCompatActivity,
    private var nowPlayingMovies: MovieEntity,
    private var tvShows: TvShowEntity,
) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return MovieTvFragment.newInstance(position + 1, nowPlayingMovies, tvShows)
    }

    override fun getItemCount(): Int {
        return 2
    }
}