package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShowEntity(
    @field:SerializedName("results")
    val onAir: ArrayList<TvShow>? = null,

    ) : Serializable

data class TvShow(

    @field:SerializedName("backdrop_path")
    val backdrop_path: String? = null,

    @field:SerializedName("first_air_date")
    val first_air_date: String? = null,

    @field:SerializedName("genre_ids")
    val genre_ids: ArrayList<Int>? = null,

    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("original_country")
    val original_country: String? = null,

    @field:SerializedName("original_language")
    val original_language: String? = null,

    @field:SerializedName("original_name")
    val original_name: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("poster_path")
    val poster_path: String? = null,

    @field:SerializedName("vote_average")
    val vote_average: Float? = null,

    @field:SerializedName("vote_count")
    val vote_count: Int? = 0,

    var genre: ArrayList<String>? = null
) : Serializable