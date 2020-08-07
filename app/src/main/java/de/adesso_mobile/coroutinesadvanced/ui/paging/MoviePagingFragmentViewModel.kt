package de.adesso_mobile.coroutinesadvanced.ui.paging

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.adesso_mobile.coroutinesadvanced.io.network.flickr.MovieService
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel

class MoviePagingFragmentViewModel(
    val moviePagingSource: MoviePagingSource,
    val movieService: MovieService
) : BaseViewModel() {

    val movies = Pager(
        PagingConfig(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        moviePagingSource
    }.flow
        .cachedIn(viewModelScope)
}