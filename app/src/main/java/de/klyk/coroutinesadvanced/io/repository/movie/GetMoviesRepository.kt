package de.klyk.coroutinesadvanced.io.repository.movie

import androidx.paging.PagingData
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import kotlinx.coroutines.flow.Flow

interface GetMoviesFlowRepository {

    fun getMovies(searchQuery: String): Flow<PagingData<Movie>>
}