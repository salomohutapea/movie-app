package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemTvMovieListBinding
import com.example.movieapp.domain.model.Movie

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(newListData: List<Movie>?) {
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
        val movie = listData[position]
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(movie)
        }
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(holder.binding.singlePosterImg)
        holder.binding.singlePopularityTextview.text = movie.voteAverage.toString()
        holder.binding.singleTitleTextview.text = movie.title
        holder.binding.singleDateReleasedTextview.text =
            StringBuilder("Date released: ${movie.releaseDate}")

        var genreText = "Genre: "

        movie.genres?.forEachIndexed { i, it ->
            genreText = if (i != movie.genres!!.size - 1) {
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
        fun onItemClicked(data: Movie)
    }

    override fun getItemCount(): Int = listData.size

}