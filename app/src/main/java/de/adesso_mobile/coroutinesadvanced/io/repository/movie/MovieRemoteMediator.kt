package de.adesso_mobile.coroutinesadvanced.io.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import de.adesso_mobile.coroutinesadvanced.io.db.movies.Movie
import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDao
import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDatabase
import de.adesso_mobile.coroutinesadvanced.io.network.movies.MovieResponse
import de.adesso_mobile.coroutinesadvanced.io.network.movies.MovieService
import de.adesso_mobile.coroutinesadvanced.io.network.movies.SearchItem
import io.ktor.client.call.receive
import timber.log.Timber
import java.io.IOException

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val database: MovieDatabase,
    private val movieDao: MovieDao,
    private val movieService: MovieService,
    private val query: String
) : RemoteMediator<Int, Movie>() {
    var page = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        Timber.d("MOVIES:::::::::: ${loadType.name}")
        Timber.d("MOVIES query:::::::::: $query")

        /** Bestimme PagingKey abhänging vom LoadType */
        val loadKey = when (loadType) {
            // Initialier Load
            LoadType.REFRESH -> {
                page = 1
                page
            }
            //Wird geladen beim Start einer PagingData
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            // Wird geladen beim Ende einer PagingData
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    Timber.d("LoadType.APPEND endOfPaginationReached = true")
                    return MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    Timber.d("LastItem: ${lastItem.title}")
                }
                page++
                page
            }
        }

        try {
            val response: List<SearchItem>
            if (query.isEmpty()) {
                return MediatorResult.Success(endOfPaginationReached = true)
            } else {
                response = movieService.fetchMovies(s = query, page = loadKey).receive<MovieResponse>().search

                val endOfPaginationReached = response.isEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDao.deleteByQuery(query)
                    }
                    val movies = response.map {
                        Movie(
                            title = it.title ?: "null",
                            type = it.type,
                            year = it.year,
                            imdbID = it.imdbID,
                            poster = it.poster
                        )
                    }

                    movieDao.insertAll(movies)
                }

                Timber.d("endOfPaginationReached = $endOfPaginationReached")
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        } catch (e: IOException) {
            Timber.d("MOVIES FAIL:::::::::: ${e.message}")
            return MediatorResult.Error(e)
        }
    }
}