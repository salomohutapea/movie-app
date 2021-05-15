package com.example.movieapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapp.data.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movie: List<Movie>)

    @Update
    fun update(movie: Movie)

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovieById(movieId: String): LiveData<List<Movie>>

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * from movie")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    fun getFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    fun getFavoriteMoviesPaging(): PagingSource<Int, Movie>
}