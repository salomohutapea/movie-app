package com.example.movieapp.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.databinding.ActivityTvDetailBinding
import com.example.movieapp.viewmodel.ViewModelFactory
import com.example.movieapp.viewmodels.DetailTvViewModel
import java.util.*

class TvDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailBinding
    private lateinit var detailTvViewModel: DetailTvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tv Show Detail"


        val factory = ViewModelFactory.getInstance(this)
        detailTvViewModel = ViewModelProvider(this, factory)[DetailTvViewModel::class.java]

        detailTvViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

        detailTvViewModel.isLoading.postValue(true)

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
            detailTvLangTextview.text = tvShow.original_language?.capitalize(Locale.ROOT)
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
        detailTvViewModel.isLoading.postValue(false)
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