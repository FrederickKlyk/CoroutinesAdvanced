package de.adesso_mobile.coroutinesadvanced.io.db.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class Movie (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @field:SerializedName("Title")
    val title: String?,

    @field:SerializedName("Type")
    val type: String?,

    @field:SerializedName("Year")
    val year: String?,

    @field:SerializedName("imdbID")
    val imdbID: String?,

    @field:SerializedName("Poster")
    val poster: String?
)