package com.salomohutapea.movieapp.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salomohutapea.movieapp.core.R
import com.salomohutapea.movieapp.core.databinding.ItemTvMovieSingleFavoriteBinding
import com.salomohutapea.movieapp.core.domain.model.TvShow

class FavoriteTvShowAdapter  :
    RecyclerView.Adapter<FavoriteTvShowAdapter.ListViewHolder>() {

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
            .inflate(R.layout.item_tv_movie_single_favorite, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = listData[position]
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(movie)
        }
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .centerCrop()
            .into(holder.binding.favSingleImage)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding = ItemTvMovieSingleFavoriteBinding.bind(itemView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }

    override fun getItemCount(): Int = listData.size

}