package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.databinding.ItemTvMovieListBinding

class TvShowsAdapter(private val listTvShows: ArrayList<TvShow>) :
    RecyclerView.Adapter<TvShowsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_tv_movie_list, viewGroup, false)
        val holder = ListViewHolder(view)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTvShows[holder.absoluteAdapterPosition]) }
        return holder
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val tvShow = listTvShows[position]
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${tvShow.posterPath}")
            .into(holder.binding.singlePosterImg)
        holder.binding.singlePopularityTextview.text = tvShow.voteAverage.toString()
        holder.binding.singleTitleTextview.text = tvShow.name
        holder.binding.singleDateReleasedTextview.text = StringBuilder("First on-air date: ${tvShow.firstAirDate}")

        var genreText = "Genre: "

        tvShow.genre?.forEachIndexed { i, it ->
            genreText = if (i != tvShow.genre!!.size - 1) {
                genreText.plus(it).plus(", ")
            } else {
                genreText.plus(it)
            }
        }

        holder.binding.singleGenreTextview.text = genreText
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding = ItemTvMovieListBinding.bind(itemView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }
}