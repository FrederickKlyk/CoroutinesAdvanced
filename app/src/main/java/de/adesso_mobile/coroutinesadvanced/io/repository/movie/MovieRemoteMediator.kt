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
import java.io.IOException

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val database: MovieDatabase,
    private val movieDao: MovieDao,
    private val movieService: MovieService
) : RemoteMediator<Int, Movie>() {
    val query = "black"
    var page = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            val response = movieService.fetchMovies(s = query, page = page).receive<MovieResponse>()
            page += 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteByQuery(query)
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                val movies = response.search.map { Movie(title = it.title, type = it.type, year = it.year, imdbID = it.imdbID, poster = it.poster) }
                movieDao.insertAll(movies)
            }

            MediatorResult.Success(endOfPaginationReached = page == 10)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }
}