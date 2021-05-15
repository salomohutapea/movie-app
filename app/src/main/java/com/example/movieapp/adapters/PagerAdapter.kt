package com.example.movieapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.views.MovieTvFragment
import com.google.android.material.switchmaterial.SwitchMaterial


class PagerAdapter(
    activity: AppCompatActivity,
    private var favoriteSwitch: SwitchMaterial
) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return MovieTvFragment.newInstance(position + 1, favoriteSwitch)
    }

    override fun getItemCount(): Int {
        return 2
    }
}