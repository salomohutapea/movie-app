package com.example.movieapp.ui.moviedetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.viewmodel.ViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*


@DelicateCoroutinesApi
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private var menu: Menu? = null
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Movie Detail"

        movie = intent.getSerializableExtra("MOVIE") as Movie

        initializeViewModel()
        initializeView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        setFavoriteState(movie.isFavorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            movieDetailViewModel.setFavorite(movie)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        movieDetailViewModel = ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        movieDetailViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

        movieDetailViewModel.isLoading.postValue(true)

        movieDetailViewModel.getFavorite().observe(this) {
            setFavoriteState(it)
        }
    }

    private fun initializeView() {
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
            textviewDetailMovieDateContent.text = movie.releaseDate
            detailMovieLangTextview.text = movie.originalLanguage?.uppercase(Locale.getDefault())
            detailMovieVoteAverageTextview.text = movie.voteAverage.toString()

            var genreText = ""
            movie.genres?.forEachIndexed { i, it ->
                genreText = if (i != movie.genres!!.size - 1) {
                    genreText.plus("•  ${it}\n")
                } else {
                    genreText.plus("•  $it")
                }
            }
            textviewDetailMovieGenresContent.text = genreText
        }
        movieDetailViewModel.isLoading.postValue(false)
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

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24_white)
        } else {
            menuItem?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24_white)
        }
    }
}