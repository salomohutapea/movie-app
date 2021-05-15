package com.example.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.dao.TvShowDao
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow

@Database(
    entities = [Movie::class, TvShow::class], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        private const val MOVIE_DB = "movie.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, MOVIE_DB
                    )
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}