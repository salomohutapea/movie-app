package com.salomohutapea.movieapp.ui

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salomohutapea.movieapp.core.domain.model.Movie
import com.salomohutapea.movieapp.core.domain.model.TvShow
import com.salomohutapea.movieapp.core.ui.MoviesAdapter
import com.salomohutapea.movieapp.core.ui.TvShowsAdapter
import com.salomohutapea.movieapp.moviedetail.MovieDetailActivity
import com.salomohutapea.movieapp.tvdetail.TvDetailActivity
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class RecyclerViewHandler {

    fun showMovieRecyclerView(
        recyclerView: RecyclerView,
        context: Context,
        list: List<Movie>
    ) {
        val adapter = MoviesAdapter()
        adapter.setData(list)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickCallback(object : MoviesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("MOVIE", data)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        })
    }

    fun showTvRecyclerView(
        recyclerView: RecyclerView,
        context: Context,
        list: List<TvShow>
    ) {
        val adapter = TvShowsAdapter()
        adapter.setData(list)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickCallback(object : TvShowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                val intent = Intent(context, TvDetailActivity::class.java)
                intent.putExtra("TVSHOW", data)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        })
    }

}