package de.klyk.coroutinesadvanced.io.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import de.klyk.coroutinesadvanced.io.network.movies.MovieResponse
import de.klyk.coroutinesadvanced.io.network.movies.MovieService
import io.ktor.client.call.receive
import timber.log.Timber
import java.io.IOException

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val database: MovieDatabase,
    private val movieService: MovieService,
    private val query: String
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        Timber.d("MOVIES:::::::::: ${loadType.name}")
        Timber.d("MOVIES query:::::::::: $query")

        /** Bestimme PagingKey abhänging vom LoadType */
        val page = when (loadType) {
            // Initialier Load
            LoadType.REFRESH -> {
                getRemoteKeyClosestToCurrentPosition(state).let { nullableRemoteKeys ->
                    nullableRemoteKeys?.nextKey?.minus(1) ?: FIRST_PAGE
                }
            }
            //Wird geladen beim Start einer PagingData
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                remoteKeys?.prevKey ?: INVALID_PAGE
            }
            // Wird geladen beim Ende einer PagingData
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                remoteKeys?.nextKey ?: INVALID_PAGE
            }
        }
        Timber.d("RemoteKey ist aktuell: $page")
        try {
            if (query.isEmpty() || page == INVALID_PAGE) {
                Timber.d("Query ist empty, oder $page == $INVALID_PAGE")
                return MediatorResult.Success(endOfPaginationReached = true)
            } else {
                val response = movieService.fetchMovies(s = query, page = page).receive<MovieResponse>()
                val movies = response.transformToMovies()
                val endOfPaginationReached = movies.isEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        Timber.d("LoadType.REFRESH: Lösche alle Remotekeys und Datensätze")
                        database.movieRemoteKeysDao().clearRemoteKeys()
                        database.movieDao().clearAllMovies()
                    }

                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = movies.map {
                        Movie.MovieRemoteKeys(movieTitle = it.title, prevKey = prevKey, nextKey = nextKey)
                    }

                    database.movieRemoteKeysDao().insertAll(keys)
                    database.movieDao().insertAll(movies)
                }

                Timber.d("endOfPaginationReached = $endOfPaginationReached")
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        } catch (e: IOException) {
            Timber.d("MOVIES FAIL:::::::::: ${e.message}")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): Movie.MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { movie ->
            database.withTransaction {
                database.movieRemoteKeysDao().remoteKeysByMovieTitle(movie.title)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): Movie.MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.withTransaction {
                database.movieRemoteKeysDao().remoteKeysByMovieTitle(movie.title)
            }
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): Movie.MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { title ->
                database.movieRemoteKeysDao().remoteKeysByMovieTitle(title)
            }
        }
    }

    private fun MovieResponse.transformToMovies(): List<Movie> {
        return this.search.map {
            Movie(
                title = it.title ?: "null",
                type = it.type,
                year = it.year,
                imdbID = it.imdbID,
                poster = it.poster
            )
        }
    }

    companion object {
        const val FIRST_PAGE = 1
        const val INVALID_PAGE = -1
    }
}