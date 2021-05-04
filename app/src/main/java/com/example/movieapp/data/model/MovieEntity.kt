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
    val genre_ids: ArrayList<Int>? = null,

    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("original_language")
    val original_language: String? = null,

    @field:SerializedName("original_title")
    val original_title: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("poster_path")
    val poster_path: String? = null,

    @field:SerializedName("release_date")
    val release_date: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = false,

    @field:SerializedName("vote_average")
    val vote_average: Float? = null,

    @field:SerializedName("vote_count")
    val vote_count: Int? = 0,

    var genre: ArrayList<String>? = null
) : Serializable