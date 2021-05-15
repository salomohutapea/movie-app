package com.example.movieapp.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.databinding.ActivityTvDetailBinding
import com.example.movieapp.viewmodels.DetailTvViewModel
import com.example.movieapp.viewmodels.ViewModelFactory
import java.util.*


class TvDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailBinding
    private lateinit var detailTvViewModel: DetailTvViewModel
    private var menu: Menu? = null
    lateinit var tvShow: TvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tv Show Detail"

        tvShow = intent.getSerializableExtra("TVSHOW") as TvShow

        initializeViewModel()
        initializeView()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        setFavoriteState(tvShow.isFavorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            detailTvViewModel.setFavorite(tvShow)
        }
        else if(item.itemId == R.id.home) {
            this.finish()
            NavUtils.navigateUpFromSameTask(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        detailTvViewModel = ViewModelProvider(this, factory)[DetailTvViewModel::class.java]

        detailTvViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

        detailTvViewModel.isLoading.postValue(true)

        detailTvViewModel.getFavorite().observe(this) {
            setFavoriteState(it)
        }
    }

    private fun initializeView() {
        with(binding) {

            Glide.with(applicationContext)
                .load("https://image.tmdb.org/t/p/w500${tvShow.backdropPath}")
                .into(imageviewDetailTvBackdrop)

            textviewDetailTvTitle.text = tvShow.name
            textviewDetailTvOverviewContent.text = tvShow.overview
            detailTvLangTextview.text = tvShow.originalLanguage?.uppercase(Locale.getDefault())
            textviewDetailTvDateContent.text = tvShow.firstAirDate
            detailTvVoteAverageTextview.text = tvShow.voteAverage.toString()

            var genreText = ""
            tvShow.genres?.forEachIndexed { i, it ->
                genreText = if (i != tvShow.genres!!.size - 1) {
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