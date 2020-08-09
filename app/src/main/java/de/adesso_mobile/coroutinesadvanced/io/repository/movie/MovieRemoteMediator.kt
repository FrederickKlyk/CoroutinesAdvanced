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
import io.ktor.client.call.receive
import timber.log.Timber
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val database: MovieDatabase,
    private val movieDao: MovieDao,
    private val movieService: MovieService
) : RemoteMediator<Int, Movie>() {
    val query = "black"
    var page = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        Timber.d("MOVIES:::::::::: ${loadType.name}")

        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            //Wird geladen beim Start einer PagingData
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            // Wird geladen beim Ende einer PagingData
            LoadType.APPEND -> {
                state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                page + 1
            }
        }

        try {
            val response = movieService.fetchMovies(s = query, page = loadKey).receive<MovieResponse>().search
            val endOfPaginationReached = response.isEmpty()
            page += 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteByQuery(query)
                }
                val movies = response.map { Movie(title = it.title ?: "null", type = it.type, year = it.year, imdbID = it.imdbID, poster = it.poster) }

                movieDao.insertAll(movies)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Timber.d("MOVIES FAIL:::::::::: ${e.message}")
            return MediatorResult.Error(e)
        }
    }
}