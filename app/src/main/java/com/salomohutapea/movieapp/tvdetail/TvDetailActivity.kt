package com.salomohutapea.movieapp.tvdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityTvDetailBinding
import com.salomohutapea.movieapp.core.domain.model.TvShow
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


@DelicateCoroutinesApi
class TvDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailBinding
    private val tvDetailViewModel: TvDetailViewModel by viewModel()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        setFavoriteState(tvShow.isFavorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            tvDetailViewModel.setFavorite(tvShow)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeViewModel() {

        tvDetailViewModel.getIsLoading().observe(this) {
            showLoading(it)
        }

        tvDetailViewModel.isLoading.postValue(true)

        tvDetailViewModel.getFavorite().observe(this) {
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
        tvDetailViewModel.isLoading.postValue(false)
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