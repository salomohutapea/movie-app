package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.ItemTvMovieListBinding

class MoviesAdapter(private val listMovies: ArrayList<Movie>) :
    PagingDataAdapter<Movie, MoviesAdapter.ListViewHolder>(
        REPO_COMPARATOR
    ) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_tv_movie_list, viewGroup, false)
        val holder = ListViewHolder(view)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMovies[holder.absoluteAdapterPosition]) }
        return holder
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = listMovies[position]
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

    override fun getItemCount(): Int {
        return listMovies.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding = ItemTvMovieListBinding.bind(itemView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}