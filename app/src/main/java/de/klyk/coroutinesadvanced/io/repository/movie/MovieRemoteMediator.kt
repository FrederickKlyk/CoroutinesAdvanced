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
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val database: MovieDatabase,
    private val movieService: MovieService
) : RemoteMediator<Int, Movie>(), RepoSearch {

    private var query: String = ""

    override fun searchQuery(query: String) {
        this.query = query
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        Timber.d("MOVIE LoadType: ${loadType.name}, ${state.anchorPosition}")

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
                val firstMovie = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.title
                Timber.d("PREPEND first movie: $firstMovie")
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Kein Previouskey für $firstMovie vorhanden."))

                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                Timber.d("PREPEND: PreviousKey: $prevKey")

                prevKey
            }
            // Wird geladen beim Ende einer PagingData
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Kein NextKey vorhanden."))
                val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)

                nextKey
            }
        }
        Timber.d("RemoteKey ist aktuell: $page")
        try {
            if (query.isEmpty()) {
                Timber.d("Query ist empty")
                return MediatorResult.Success(endOfPaginationReached = true)
            } else {
                val response = movieService.fetchMovies(s = query, page = page).receive<MovieResponse>()
                val movies = response.transformToMovies(page)
                val endOfPaginationReached = movies.isEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        Timber.d("LoadType.REFRESH: Lösche alle Remotekeys und Datensätze")
                        database.movieRemoteKeysDao().clearRemoteKeys()
                        database.movieDao().clearAllMovies()
                    }

                    // RemoteKeys werden gesammelt für eine Page (z.B. 1-10) und gespeichert
                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = movies.map {
                        Movie.MovieRemoteKeys(imdbID = it.imdbID, prevKey = prevKey, nextKey = nextKey)
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
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            Timber.d("RemoteKeyForLastItem: ${repo.title}")
            database.movieRemoteKeysDao().remoteKeysByMovieTitle(repo.imdbID)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): Movie.MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.movieRemoteKeysDao().remoteKeysByMovieTitle(movie.imdbID)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): Movie.MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.imdbID?.let { imdbID ->
                database.movieRemoteKeysDao().remoteKeysByMovieTitle(imdbID)
            }
        }
    }

    private fun MovieResponse.transformToMovies(page: Int): List<Movie> {
        return this.search.map {
            Movie(
                id = 0,
                title = it.title ?: "null",
                type = it.type,
                year = it.year,
                imdbID = it.imdbID ?: "null",
                poster = it.poster,
                page = page
            )
        }
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}