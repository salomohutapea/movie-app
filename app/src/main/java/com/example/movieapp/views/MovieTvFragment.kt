package com.example.movieapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieTvBinding
import com.example.movieapp.handlers.RecyclerViewHandler
import com.example.movieapp.viewmodels.FragmentMovieTvViewModel
import com.example.movieapp.viewmodels.ViewModelFactory
import com.example.movieapp.vo.Status


class MovieTvFragment : Fragment() {
    private lateinit var rvMovieTv: RecyclerView
    private lateinit var binding: FragmentMovieTvBinding
    private lateinit var fragmentViewModel: FragmentMovieTvViewModel

    private var isShowingFavorite = false
    private var index = 0
    private var rvHandler = RecyclerViewHandler()

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(index: Int, isShowingFavorite: Boolean) =
            MovieTvFragment().apply {
                this.isShowingFavorite = isShowingFavorite
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieTvBinding.inflate(inflater, container, false)
        val factory = requireContext().let { ViewModelFactory.getInstance(it) }
        fragmentViewModel =
            ViewModelProvider(this, factory).get(FragmentMovieTvViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovieTv = view.findViewById(R.id.rv_movietv)
    }

    override fun onResume() {
        super.onResume()
        index = arguments?.getInt(ARG_SECTION_NUMBER, 0)!!
        initializeObservers()
    }

    private fun initializeObservers() {
        showLoading(true)
        Log.d("SAMPEEE", isShowingFavorite.toString())

        // All
        if (!isShowingFavorite) {
            when (index) {
                1 -> {
                    fragmentViewModel.getAllMovies().observe(viewLifecycleOwner) { movies ->
                        when (movies.status) {
                            Status.LOADING -> Log.d("STATUS_GETDATA", "LOADING GET MOVIES")
                            Status.SUCCESS -> {
                                Log.d("STATUS_GETDATA", "SUCCESS GET MOVIES")
                                context?.let {
                                    movies.data.let { it1 ->
                                        if (it1 != null) {
                                            rvHandler.showMovieRecyclerView(
                                                rvMovieTv,
                                                it,
                                                it1
                                            )
                                        }
                                    }
                                }
                                showLoading(false)
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Terjadi kesalahan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                showLoading(false)
                            }
                        }
                    }
                }
                2 -> {
                    fragmentViewModel.getAllTvShows().observe(viewLifecycleOwner) { tvShows ->
                        when (tvShows.status) {
                            Status.LOADING -> Log.d("STATUS_GETDATA", "LOADING GET TV SHOWS")
                            Status.SUCCESS -> {
                                Log.d("STATUS_GETDATA", "SUCCESS GET TV SHOWS")
                                context?.let {
                                    tvShows.data.let { it1 ->
                                        if (it1 != null) {
                                            rvHandler.showTvRecyclerView(
                                                rvMovieTv,
                                                it,
                                                it1
                                            )
                                        }
                                    }
                                }
                                showLoading(false)
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Terjadi kesalahan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                showLoading(false)
                            }
                        }
                    }
                }
            }
        }

        // Favorite only
        else {
            when (index) {
                1 -> {
                    fragmentViewModel.getFavoriteMovies()
                        .observe(viewLifecycleOwner) { movies ->
                            Log.d("STATUS_GETDATA", "SUCCESS GET FAVORITE MOVIES")
                            context?.let {
                                movies.let { it1 ->
                                    rvHandler.showMovieRecyclerView(
                                        rvMovieTv,
                                        it,
                                        it1
                                    )
                                }
                            }
                            showLoading(false)
                        }
                }
                2 -> {
                    fragmentViewModel.getFavoriteTvShows()
                        .observe(viewLifecycleOwner) { tvShows ->
                            Log.d("STATUS_GETDATA", "SUCCESS GET FAVORITE TVSHOWS")
                            context?.let {
                                tvShows.let { it1 ->
                                    rvHandler.showTvRecyclerView(
                                        rvMovieTv,
                                        it,
                                        it1
                                    )
                                }
                            }
                            showLoading(false)
                        }
                }
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressFragment.visibility = View.VISIBLE
        } else {
            binding.progressFragment.visibility = View.GONE
        }
    }
}