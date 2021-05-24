package com.salomohutapea.movieapp.movietvfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieTvBinding
import com.salomohutapea.movieapp.core.data.Resource
import com.salomohutapea.movieapp.core.ui.RecyclerViewHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@DelicateCoroutinesApi
class MovieTvFragment : Fragment() {
    private lateinit var rvMovieTv: RecyclerView
    private lateinit var binding: FragmentMovieTvBinding
    private val fragmentViewModel: MovieTvViewModel by viewModel()

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

        // All
        if (!isShowingFavorite) {
            when (index) {
                1 -> {
                    fragmentViewModel.getAllMovies().observe(viewLifecycleOwner) { movies ->
                        if (movies != null) {
                            when (movies) {
                                is Resource.Loading -> Log.d("STATUS_GETDATA", "LOADING GET MOVIES")
                                is Resource.Success -> {
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
                                is Resource.Error -> {
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
                2 -> {
                    fragmentViewModel.getAllTvShows().observe(viewLifecycleOwner) { tvShows ->
                        if (tvShows != null) {
                            when (tvShows) {
                                is Resource.Loading -> Log.d("STATUS_GETDATA", "LOADING GET TVSHOWS")
                                is Resource.Success -> {
                                    Log.d("STATUS_GETDATA", "SUCCESS GET TVSHOWS")
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
                                is Resource.Error -> {
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