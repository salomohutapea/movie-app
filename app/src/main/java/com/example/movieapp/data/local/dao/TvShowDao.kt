package com.example.movieapp.data.local.dao

import androidx.room.*
import com.example.movieapp.data.model.TvShow
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvShows(tvShow: List<TvShow>)

    @Update
    fun update(tvShow: TvShow)

    @Delete
    fun delete(tvShow: TvShow)

    @Query("SELECT * from tvshow")
    fun getAllTvShows(): Flow<List<TvShow>>

    @Query("SELECT * FROM tvshow WHERE is_favorite = 1")
    fun getFavoriteTvShows(): Flow<List<TvShow>>
}