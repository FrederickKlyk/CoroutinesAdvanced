package de.klyk.coroutinesadvanced.io.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import kotlinx.coroutines.flow.Flow


class GetMoviesFlowRepositoryImpl(
    val movieDatabase: MovieDatabase,
    val movieRemoteMediator: MovieRemoteMediator
) : GetMoviesFlowRepository {

    override fun getMovies(searchQuery: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 20, enablePlaceholders = true, maxSize = 30),
            remoteMediator = movieRemoteMediator.apply { this.searchQuery(searchQuery) },
            pagingSourceFactory = { movieDatabase.movieDao().getDatabasePagingSource("%${searchQuery}%") }
        ).flow
    }
}