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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import timber.log.Timber

class MoviePagingRemoteFragmentViewModel(
    val movieDatabase: MovieDatabase,
    val getMoviesFlowRepository: GetMoviesFlowRepository
) : BaseViewModel() {

    val searchQuery = MutableLiveData("")

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieDatabase.movieDao().clearAllMovies()
            }
        }
    }

    fun resetSerachQuery() {
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
                when {
                    before == null -> return@insertSeparators MovieModel.SeperatorItem("1")
                    after == null -> return@insertSeparators MovieModel.SeperatorItem("Ende der Liste")
                    before.movie.page < after.movie.page -> MovieModel.SeperatorItem(after.movie.page.toString())
                    else -> null
                }
            }
        }.cachedIn(viewModelScope)
}
