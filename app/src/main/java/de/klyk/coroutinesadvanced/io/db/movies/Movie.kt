package de.klyk.coroutinesadvanced.io.db.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class Movie (

    @PrimaryKey
    @field:SerializedName("Title")
    var title: String = "",

    @field:SerializedName("Type")
    val type: String?,

    @field:SerializedName("Year")
    val year: String?,

    @field:SerializedName("imdbID")
    val imdbID: String?,

    @field:SerializedName("Poster")
    val poster: String?
)