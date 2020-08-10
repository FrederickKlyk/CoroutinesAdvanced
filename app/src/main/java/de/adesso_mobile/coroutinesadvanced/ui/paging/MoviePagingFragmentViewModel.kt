package de.adesso_mobile.coroutinesadvanced.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDao
import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDatabase
import de.adesso_mobile.coroutinesadvanced.io.network.movies.MovieService
import de.adesso_mobile.coroutinesadvanced.io.repository.movie.MovieRemoteMediator
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber

class MoviePagingFragmentViewModel(
    val moviePagingSource: MoviePagingSource,
    val movieDatabase: MovieDatabase,
    val movieDao: MovieDao,
    val movieService: MovieService
) : BaseViewModel() {

    val searchQuery = MutableLiveData("")

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { movieDao.getMovies() }
                .apply {
                    Timber.d("Aktuelle Moviesize: $size")
                }
        }
    }

    /** Pager Konfiguration mit einem RemoteMediator */
    @ExperimentalPagingApi
    fun moviePagerFlow(query: String) = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false, maxSize = 200),
        remoteMediator = MovieRemoteMediator(
            database = movieDatabase,
            movieDao = movieDao,
            movieService = movieService,
            query = query
        )
    ) {
        movieDao.getDatabasePagingSource("%${query}%")
    }.flow

    /** Sucheingabe triggerd eine neue Suche aus */
    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    val movieFlow = searchQuery
        .asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { moviePagerFlow(it) }
}
