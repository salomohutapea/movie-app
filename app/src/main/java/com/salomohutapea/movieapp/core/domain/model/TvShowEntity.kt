package com.salomohutapea.movieapp.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShowEntity(
    @field:SerializedName("results")
    val tvShow: ArrayList<TvShow>? = null,

    ) : Serializable

@Entity(tableName = "tvshow")
data class TvShow(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int? = 0,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @ColumnInfo(name = "first_air_date")
    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @ColumnInfo(name = "genre_ids")
    @field:SerializedName("genre_ids")
    val genreIds: ArrayList<String>? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "original_country")
    @field:SerializedName("original_country")
    val originalCountry: String? = null,

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @ColumnInfo(name = "original_name")
    @field:SerializedName("original_name")
    val originalName: String? = null,

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    val overview: String? = null,

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    val posterPath: String? = null,

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