package com.example.movieapp.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.viewmodel.ViewModelFactory
import com.example.movieapp.viewmodels.DetailMovieViewModel
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var detailMovieViewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Movie Detail"

        val factory = ViewModelFactory.getInstance(this)
        detailMovieViewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        detailMovieViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

        detailMovieViewModel.isLoading.postValue(true)

        initializeView()

    }

    private fun initializeView() {
        val movie = intent.getSerializableExtra("MOVIE") as Movie
        Log.d("ADUULT", movie.adult.toString())
        with(binding) {
            if (movie.adult == true) {
                cardDetailMovieAdult.visibility = View.VISIBLE
            } else {
                cardDetailMovieAdult.visibility = View.GONE
            }

            Glide.with(applicationContext)
                .load("https://image.tmdb.org/t/p/w500${movie.backdrop_path}")
                .into(imageviewDetailMovieBackdrop)

            textviewDetailMovieTitle.text = movie.title
            textviewDetailMovieOverviewContent.text = movie.overview
            textviewDetailMovieDateContent.text = movie.release_date
            detailMovieLangTextview.text = movie.original_language?.capitalize(Locale.ROOT)
            detailMovieVoteAverageTextview.text = movie.vote_average.toString()

            var genreText = ""
            movie.genre?.forEachIndexed { i, it ->
                genreText = if (i != movie.genre!!.size - 1) {
                    genreText.plus("•  ${it}\n")
                } else {
                    genreText.plus("•  $it")
                }
            }
            textviewDetailMovieGenresContent.text = genreText
        }
        detailMovieViewModel.isLoading.postValue(false)
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.constraintDetailMovieAll.visibility = View.GONE
            binding.progressDetailMovie.visibility = View.VISIBLE
        } else {
            binding.progressDetailMovie.visibility = View.GONE
            binding.constraintDetailMovieAll.visibility = View.VISIBLE
        }
    }
}