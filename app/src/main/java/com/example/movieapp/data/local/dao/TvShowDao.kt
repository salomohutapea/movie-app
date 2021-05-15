package com.example.movieapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapp.data.model.TvShow

@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShows(tvShow: List<TvShow>)

    @Update
    fun update(tvShow: TvShow)

    @Delete
    fun delete(tvShow: TvShow)

    @Query("SELECT * from tvshow")
    fun getAllTvShows(): LiveData<List<TvShow>>

    @Query("SELECT * FROM tvshow WHERE is_favorite = 1")
    fun getFavoriteTvShows(): LiveData<List<TvShow>>

    @Query("SELECT * FROM tvshow WHERE id = :tvShowId")
    fun getMovieById(tvShowId: String): LiveData<List<TvShow>>

    @Query("SELECT * FROM tvshow WHERE is_favorite = 1")
    fun getFavoriteTvShowPaging(): PagingSource<Int, TvShow>
}