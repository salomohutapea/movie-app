package com.example.movieapp.core.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.core.data.source.local.dao.MovieDao
import com.example.movieapp.core.data.source.local.dao.TvShowDao
import com.example.movieapp.core.domain.model.Movie
import com.example.movieapp.core.domain.model.TvShow

@Database(
    entities = [Movie::class, TvShow::class], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

    }
}