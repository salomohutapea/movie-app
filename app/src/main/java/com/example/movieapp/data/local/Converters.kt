package com.example.movieapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Converters {
    @TypeConverter
    fun toStringArrayList(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

//    @TypeConverter
//    fun toIntArrayList(value: String?): ArrayList<Int> {
//        val listType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromIntArrayList(list: ArrayList<Int?>?): String {
//        val gson = Gson()
//        return gson.toJson(list)
//    }
//
//    @TypeConverter
//    fun toMovieArray(value: String?): ArrayList<Movie> {
//        val listType: Type = object : TypeToken<ArrayList<Movie?>?>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromMovieArray(value: ArrayList<Movie>): String {
//        val type = object : TypeToken<ArrayList<Movie?>?>() {}.type
//        return Gson().toJson(value, type)
//    }
//
//    @TypeConverter
//    fun toTvArray(value: String?): ArrayList<TvShow> {
//        val listType: Type = object : TypeToken<ArrayList<TvShow?>?>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromTvArray(value: ArrayList<TvShow>): String {
//        val type = object : TypeToken<ArrayList<TvShow?>?>() {}.type
//        return Gson().toJson(value, type)
//    }
}