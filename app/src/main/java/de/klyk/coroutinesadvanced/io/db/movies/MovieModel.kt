package de.klyk.coroutinesadvanced.io.db.movies

sealed class MovieModel {

    data class MovieItem(val movie: Movie) : MovieModel()
    data class SeperatorItem(val description: String = "") : MovieModel()
}