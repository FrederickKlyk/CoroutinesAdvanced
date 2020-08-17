package de.klyk.coroutinesadvanced.io.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import kotlinx.coroutines.flow.Flow

class GetMoviesSourceFlowRepositoryImpl(
    private val moviePagingSource: MoviePagingSource,
    private val database: MovieDatabase
) : GetMoviesFlowRepository {

    override fun getMovies(searchQuery: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                initialLoadSize = 40
            ),
            //pagingSourceFactory = { database.movieDao().getDatabasePagingSource("%${searchQuery}%") } // DB Room Anfrage
            pagingSourceFactory = { moviePagingSource.apply { this.searchQuery(searchQuery) } }
        ).flow
    }
}