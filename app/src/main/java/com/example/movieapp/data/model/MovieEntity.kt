package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieEntity(
    @field:SerializedName("results")
    val movies: ArrayList<Movie>? = null,

    ) : Serializable

data class Movie(
    @field:SerializedName("adult")
    val adult: Boolean? = false,

    @field:SerializedName("backdrop_path")
    val backdrop_path: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: ArrayList<Int>? = null,

    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = false,

    @field:SerializedName("vote_average")
    val voteAverage: Float? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = 0,

    var genre: ArrayList<String>? = null
) : Serializable