package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.TvShow
import com.example.movieapp.databinding.ItemTvMovieListBinding

class TvShowsAdapter :
    PagedListAdapter<TvShow, TvShowsAdapter.ListViewHolder>(
        REPO_COMPARATOR
    ) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem.id == newItem.id
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_tv_movie_list, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val tvShow = getItem(position)

        if (tvShow != null) {
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(
                    tvShow
                )
            }
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500${tvShow.posterPath}")
                .into(holder.binding.singlePosterImg)
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
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding = ItemTvMovieListBinding.bind(itemView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }
}