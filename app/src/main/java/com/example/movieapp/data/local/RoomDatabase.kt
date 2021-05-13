package com.example.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.TvShow

@Database(
    entities = [Movie::class, TvShow::class], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)

abstract class RoomDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null

        @JvmStatic
        fun getInstance(context: Context): RoomDb {
            if (INSTANCE == null) {
                synchronized(RoomDb::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDb::class.java, "database"
                    )
                        .build()
                }
            }
            return INSTANCE as RoomDb
        }
    }
}