package de.klyk.coroutinesadvanced.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import de.klyk.coroutinesadvanced.io.db.movies.MovieModel
import de.klyk.coroutinesadvanced.io.network.movies.MovieService
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesFlowRepository
import de.klyk.coroutinesadvanced.io.repository.movie.MovieRemoteMediator
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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

    /** Sucheingabe triggerd eine neue Suche aus */
    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    val movieFlow = searchQuery
        .asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { getMoviesFlowRepository.getMovies(it) }
        .map { pagingData -> pagingData.map { MovieModel.MovieItem(it)}}
        .map {
            it.insertSeparators<MovieModel.MovieItem, MovieModel> { before, after ->
                if (after == null) {
                    // we're at the end of the list
                    return@insertSeparators MovieModel.SeperatorItem("Footer")
                }

                val alphabet = after.movie.title.replace("The", "").trim().take(1)

                if (before == null) {
                    // we're at the beginning of the list
                    return@insertSeparators MovieModel.SeperatorItem("HEADER")
                }

                if (before.movie.title.replace("The", "").trim().take(1) != alphabet) {
                    MovieModel.SeperatorItem(alphabet)
                } else {
                    null
                }
            }
        }.cachedIn(viewModelScope)
}
