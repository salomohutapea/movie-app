package com.salomohutapea.movieapp.core.data.source.local.dao

import androidx.room.*
import com.salomohutapea.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movie: List<Movie>)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * from movie")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    fun getFavoriteMovies(): Flow<List<Movie>>
}