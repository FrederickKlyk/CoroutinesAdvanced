package de.klyk.coroutinesadvanced.io.network.movies

import com.google.gson.annotations.SerializedName


data class MovieResponse(

    @field:SerializedName("Response")
    val response: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: String? = null,

    @field:SerializedName("Search")
    val search: List<SearchItem> = listOf()
)

data class SearchItem(

    @field:SerializedName("Type")
    val type: String? = null,

    @field:SerializedName("Year")
    val year: String? = null,

    @field:SerializedName("imdbID")
    val imdbID: String? = null,

    @field:SerializedName("Poster")
    val poster: String? = null,

    @field:SerializedName("Title")
    val title: String? = null
)
