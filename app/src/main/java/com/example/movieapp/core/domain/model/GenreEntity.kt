package com.example.movieapp.core.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GenreEntity(

    @field:SerializedName("genres")
    val genres: ArrayList<Genre>? = null
) : Serializable

data class Genre(
    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("name")
    val name: String? = null
) : Serializable