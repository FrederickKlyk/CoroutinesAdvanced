package de.adesso_mobile.coroutinesadvanced.ui.paging

import androidx.paging.PagingSource
import de.adesso_mobile.coroutinesadvanced.io.network.flickr.MovieService
import io.ktor.client.call.receive
import timber.log.Timber

class MoviePagingSource(
    private val movieService: MovieService
) : PagingSource<Int, SearchItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        Timber.d("Starte Netzwerkcall")

        return try {
            val nextPage = params.key ?: 1
            val response = movieService.fetchMovies("black", nextPage)

            LoadResult.Page(
                data = (response.receive() as MovieResponse).search,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            Timber.d("Netzwerkcall fehlgeschlagen")
            LoadResult.Error(e)
        }
    }
}