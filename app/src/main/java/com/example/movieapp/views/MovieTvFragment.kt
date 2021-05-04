package com.example.movieapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.TvShowEntity
import com.example.movieapp.databinding.FragmentMovieTvBinding
import com.example.movieapp.handlers.ListHandler

class MovieTvFragment : Fragment() {
    private lateinit var rvMovieTv: RecyclerView
    private lateinit var binding: FragmentMovieTvBinding
    private var rvHandler = ListHandler()

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val MOVIES = "movies"
        private const val TV_SHOWS = "tv_shows"

        @JvmStatic
        fun newInstance(index: Int, movieEntity: MovieEntity, tvShows: TvShowEntity) =
            MovieTvFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putSerializable(MOVIES, movieEntity)
                    putSerializable(TV_SHOWS, tvShows)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieTvBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovieTv = view.findViewById(R.id.rv_movietv)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        if (index == 1) {

            val list = arguments?.getSerializable(MOVIES) as MovieEntity
            context?.let {
                list.movies?.let { it1 -> rvHandler.showMovieRecyclerView(rvMovieTv, it, it1) }
            }
        } else if (index == 2) {
            val list = arguments?.getSerializable(TV_SHOWS) as TvShowEntity
            context?.let {
                list.onAir?.let { it1 -> rvHandler.showTvRecyclerView(rvMovieTv, it, it1) }
            }
        }
    }
}