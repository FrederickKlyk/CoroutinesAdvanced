package de.klyk.coroutinesadvanced.io.repository.movie

import androidx.paging.PagingSource
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import de.klyk.coroutinesadvanced.io.network.movies.MovieResponse
import de.klyk.coroutinesadvanced.io.network.movies.MovieService
import de.klyk.coroutinesadvanced.io.network.movies.SearchItem
import de.klyk.coroutinesadvanced.io.repository.movie.RepoSearch
import io.ktor.client.call.receive
import timber.log.Timber

class MoviePagingSource(
    private val movieService: MovieService
) : PagingSource<Int, Movie>(), RepoSearch {

    private var query: String = ""

    override fun searchQuery(query: String) {
        this.query = query
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Timber.d("Starte Netzwerkcall")

        return try {
            val nextPage = params.key ?: 1
            val response = movieService.fetchMovies(s = query, page = nextPage).receive<MovieResponse>()
            val movies = response.transformToMovies()

            LoadResult.Page(
                data = movies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            Timber.d("Netzwerkcall fehlgeschlagen")
            LoadResult.Error(e)
        }
    }

    private fun MovieResponse.transformToMovies(): List<Movie> {
        return this.search.map {
            Movie(
                id = 0,
                title = it.title ?: "null",
                type = it.type,
                year = it.year,
                imdbID = it.imdbID ?: "null",
                poster = it.poster
            )
        }
    }
}