package com.salomohutapea.movieapp.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salomohutapea.movieapp.core.R
import com.salomohutapea.movieapp.core.databinding.ItemTvMovieListBinding
import com.salomohutapea.movieapp.core.domain.model.TvShow
import java.util.*

class TvShowsAdapter :
    RecyclerView.Adapter<TvShowsAdapter.ListViewHolder>() {

    private var listData = ArrayList<TvShow>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<TvShow>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_tv_movie_list, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val tvShow = listData[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                tvShow
            )
        }
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${tvShow.posterPath}")
            .into(holder.binding.singlePosterImg)
        holder.binding.singleCategoryTextview.text = StringBuilder("Tv Show")
        holder.binding.singlePopularityTextview.text = tvShow.voteAverage.toString()
        holder.binding.singleTitleTextview.text = tvShow.name
        holder.binding.singleDateReleasedTextview.text =
            StringBuilder("First on-air date: ${tvShow.firstAirDate}")

        var genreText = "Genre: "

        tvShow.genres?.forEachIndexed { i, it ->
            genreText = if (i != tvShow.genres!!.size - 1) {
                genreText.plus(it).plus(", ")
            } else {
                genreText.plus(it)
            }
        }

        holder.binding.singleGenreTextview.text = genreText
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding = ItemTvMovieListBinding.bind(itemView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }

    override fun getItemCount(): Int = listData.size

}