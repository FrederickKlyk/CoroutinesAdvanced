package de.klyk.coroutinesadvanced.io.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import kotlinx.coroutines.flow.Flow

class GetMoviesSourceFlowRepositoryImpl(
    private val moviePagingSource: MoviePagingSource
) : GetMoviesFlowRepository {

    override fun getMovies(searchQuery: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { moviePagingSource.apply { this.searchQuery(searchQuery) } }
        ).flow
    }
}