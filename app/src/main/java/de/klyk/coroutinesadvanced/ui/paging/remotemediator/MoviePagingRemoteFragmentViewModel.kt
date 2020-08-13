package de.klyk.coroutinesadvanced.ui.paging.remotemediator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import de.klyk.coroutinesadvanced.io.db.movies.MovieModel
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesFlowRepository
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

class MoviePagingFragmentViewModel(
    val movieDatabase: MovieDatabase,
    val getMoviesFlowRepository: GetMoviesFlowRepository
) : BaseViewModel() {

    val searchQuery = MutableLiveData("")

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieDatabase.movieDao().clearAllMovies()
                movieDatabase.movieDao().getMovies()
            }.apply {
                Timber.d("Aktuelle Moviesize: $size")
            }
        }
    }

    fun resetSerachQuery(){
        searchQuery.value = ""
    }

    /** Sucheingabe triggerd eine neue Suche aus */
    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    val movieFlow = searchQuery
        .asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { getMoviesFlowRepository.getMovies(it) }
        .map { pagingData -> pagingData.map { MovieModel.MovieItem(it) } }
        .map {
            it.insertSeparators<MovieModel.MovieItem, MovieModel> { before, after ->
                if (after == null) {
                    // we're at the end of the list
                    return@insertSeparators MovieModel.SeperatorItem("Ende der Liste")
                }

                Timber.d("Before: ${before?.movie?.title}, After: ${after.movie.title}")

                val alphabet = after.movie.title.replace("The", "").trim().take(1)

                if (before == null) {
                    // we're at the beginning of the list
                    return@insertSeparators MovieModel.SeperatorItem(alphabet)
                }

                if (before.movie.title.replace("The", "").trim().take(1) != alphabet) {
                    MovieModel.SeperatorItem(alphabet)
                } else {
                    null
                }
            }
        }.cachedIn(viewModelScope)
}
