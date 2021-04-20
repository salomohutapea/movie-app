package com.example.movieapp.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityTvDetailBinding
import com.example.movieapp.models.TvShow
import com.example.movieapp.viewmodels.DetailTvViewModel

class TvDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailBinding
    private lateinit var detailMovieViewModel: DetailTvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tv Show Detail"

        detailMovieViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailTvViewModel::class.java)

        detailMovieViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

        detailMovieViewModel.isLoading.postValue(true)

        initializeView()

    }

    private fun initializeView() {
        val tvShow = intent.getSerializableExtra("TVSHOW") as TvShow
        with(binding) {

            Glide.with(applicationContext)
                .load("https://image.tmdb.org/t/p/w500${tvShow.backdrop_path}")
                .into(imageviewDetailTvBackdrop)

            textviewDetailTvTitle.text = tvShow.name
            textviewDetailTvOverviewContent.text = tvShow.overview
            textviewDetailTvDateContent.text = tvShow.first_air_date
            detailTvVoteAverageTextview.text = tvShow.vote_average.toString()

            var genreText = ""
            tvShow.genre?.forEachIndexed { i, it ->
                genreText = if (i != tvShow.genre!!.size - 1) {
                    genreText.plus("•  ${it}\n")
                } else {
                    genreText.plus("•  $it")
                }
            }
            textviewDetailTvGenresContent.text = genreText
        }
        detailMovieViewModel.isLoading.postValue(false)
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.constraintDetailTvAll.visibility = View.GONE
            binding.progressDetailTv.visibility = View.VISIBLE
        } else {
            binding.progressDetailTv.visibility = View.GONE
            binding.constraintDetailTvAll.visibility = View.VISIBLE
        }
    }
}