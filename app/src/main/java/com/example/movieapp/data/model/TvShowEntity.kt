package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShowEntity(
    @field:SerializedName("results")
    val tvShow: ArrayList<TvShow>? = null,

    ) : Serializable

data class TvShow(

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: ArrayList<Int>? = null,

    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("original_country")
    val originalCountry: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_name")
    val originalName: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Float? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = 0,

    var genre: ArrayList<String>? = null
) : Serializable