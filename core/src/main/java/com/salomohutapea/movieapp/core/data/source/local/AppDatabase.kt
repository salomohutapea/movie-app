package com.salomohutapea.movieapp.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.salomohutapea.movieapp.core.data.source.local.dao.MovieDao
import com.salomohutapea.movieapp.core.data.source.local.dao.TvShowDao
import com.salomohutapea.movieapp.core.domain.model.Movie
import com.salomohutapea.movieapp.core.domain.model.TvShow

@Database(
    entities = [Movie::class, TvShow::class], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

}