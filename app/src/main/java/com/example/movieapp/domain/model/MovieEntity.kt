package com.example.movieapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieEntity(
    @field:SerializedName("results")
    val movies: ArrayList<Movie>? = null,

    ) : Serializable

@Entity(tableName = "movie")
data class Movie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int? = 0,

    @ColumnInfo(name = "adult")
    @field:SerializedName("adult")
    val adult: Boolean? = false,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    val backdrop_path: String? = null,

    @ColumnInfo(name = "genre_ids")
    @field:SerializedName("genre_ids")
    val genreIds: ArrayList<String>? = null,

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @ColumnInfo(name = "original_title")
    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    val overview: String? = null,

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    val title: String? = null,

    @ColumnInfo(name = "video")
    @field:SerializedName("video")
    val video: Boolean? = false,

    @ColumnInfo(name = "vote_average")
    @field:SerializedName("vote_average")
    val voteAverage: Float? = null,

    @ColumnInfo(name = "vote_count")
    @field:SerializedName("vote_count")
    val voteCount: Int? = 0,

    @ColumnInfo(name = "genre")
    var genres: ArrayList<String>? = null,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
) : Serializable