package com.example.movieapp.handlers

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapters.MoviesAdapter
import com.example.movieapp.adapters.TvShowsAdapter
import com.example.movieapp.models.Movie
import com.example.movieapp.models.TvShow

class ListHandler {

    fun showMovieRecyclerView(
        recyclerView: RecyclerView,
        context: Context,
        list: ArrayList<Movie>
    ) {
        val adapter =  MoviesAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickCallback(object : MoviesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                Toast.makeText(context, "ITEM CLICKED", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, DetailActivity::class.java)
//                intent.putExtra("ID", data.id)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
            }
        })
    }

    fun showTvRecyclerView(
        recyclerView: RecyclerView,
        context: Context,
        list: ArrayList<TvShow>
    ) {
        val adapter =  TvShowsAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickCallback(object : TvShowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                Toast.makeText(context, "ITEM CLICKED", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, DetailActivity::class.java)
//                intent.putExtra("ID", data.id)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
            }
        })
    }

}