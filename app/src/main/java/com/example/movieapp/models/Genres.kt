package com.example.movieapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Genres(

    @field:SerializedName("genres")
    val genres: ArrayList<Genre>? = null
) : Serializable

data class Genre(
    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("name")
    val name: String? = null
) : Serializable