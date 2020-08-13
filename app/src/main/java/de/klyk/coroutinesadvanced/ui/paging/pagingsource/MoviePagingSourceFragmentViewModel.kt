package de.klyk.coroutinesadvanced.ui.paging.pagingsource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesFlowRepository
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class MoviePagingSourceFragmentViewModel(
    val getMoviesFlowRepository: GetMoviesFlowRepository
) : BaseViewModel() {

    val searchQuery = MutableLiveData("")

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
        .cachedIn(viewModelScope)
}