package de.adesso_mobile.coroutinesadvanced.ui.paging

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.adesso_mobile.coroutinesadvanced.io.db.movies.Movie
import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDao
import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDatabase
import de.adesso_mobile.coroutinesadvanced.io.network.movies.MovieService
import de.adesso_mobile.coroutinesadvanced.io.repository.movie.MovieRemoteMediator
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviePagingFragmentViewModel(
    val moviePagingSource: MoviePagingSource,
    val movieDatabase: MovieDatabase,
    val movieDao: MovieDao,
    val movieService: MovieService
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieDao.clearAll()
            }
        }
    }


    @ExperimentalPagingApi
    val movies = Pager(
        config = PagingConfig(pageSize = 50, enablePlaceholders = false, maxSize = 200),
        remoteMediator = MovieRemoteMediator(database = movieDatabase, movieDao = movieDao, movieService = movieService)
    ) {
        movieDao.pagingSource("%black%")
    }.flow
        .cachedIn(viewModelScope)
}
