package com.example.movieapp.handlers

import android.content.Context
import android.content.Intent

import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapters.MoviesAdapter
import com.example.movieapp.adapters.TvShowsAdapter
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.views.MovieDetailActivity
import com.example.movieapp.views.TvDetailActivity


class RecyclerViewHandler {

    fun showMovieRecyclerView(
        recyclerView: RecyclerView,
        context: Context,
        list: PagedList<Movie>
    ) {
        val adapter = MoviesAdapter()
        adapter.submitList(list)
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
        list: PagedList<TvShow>
    ) {
        val adapter = TvShowsAdapter()
        adapter.submitList(list)
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