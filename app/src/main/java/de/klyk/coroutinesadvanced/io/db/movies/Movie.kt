package de.klyk.coroutinesadvanced.io.db.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import de.klyk.coroutinesadvanced.io.network.movies.MovieResponse

@Entity(tableName = "movie")
data class Movie (

    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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
){
    @Entity(tableName = "movie_remote_keys")
    data class MovieRemoteKeys(
        @PrimaryKey val movieTitle: String,
        val prevKey: Int?,
        val nextKey: Int?
    )
}