package de.klyk.coroutinesadvanced.di

import de.klyk.coroutinesadvanced.ui.list.ListFragmentViewModel
import de.klyk.coroutinesadvanced.ui.main.MainActivityViewModel
import de.klyk.coroutinesadvanced.ui.main.coroutines.CoroutinesFragmentViewModel
import de.klyk.coroutinesadvanced.ui.main.coroutines.FlowFragmentViewModel
import de.klyk.coroutinesadvanced.ui.main.coroutines.StateSharedFlowFragmentViewModel
import de.klyk.coroutinesadvanced.ui.main.coroutines.WebsocketsFragmentViewModel
import de.klyk.coroutinesadvanced.ui.main.tabs.TabContainerViewModel
import de.klyk.coroutinesadvanced.ui.overviewlibs.OverviewLibsFragmentViewModel
import de.klyk.coroutinesadvanced.ui.paging.pagingsource.MoviePagingSourceFragmentViewModel
import de.klyk.coroutinesadvanced.ui.paging.remotemediator.MoviePagingRemoteFragmentViewModel
import de.klyk.coroutinesadvanced.ui.viewpager2.Viewpager2SharedViewModel
import de.klyk.coroutinesadvanced.ui.viewpager2.tabs.Viewpager2TabsPagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { TabContainerViewModel() }
    viewModel { CoroutinesFragmentViewModel(weatherInteractor = get(), lokalServerInteractor = get()) }
    viewModel { OverviewLibsFragmentViewModel() }
    viewModel { Viewpager2SharedViewModel() }
    viewModel { Viewpager2TabsPagesViewModel() }
    viewModel { FlowFragmentViewModel(weatherInteractor = get()) }
    viewModel { WebsocketsFragmentViewModel(websocketClient = get(named(DEFAULT_HTTP_CLIENT))) }
    viewModel { ListFragmentViewModel() }
    viewModel { MoviePagingSourceFragmentViewModel(getMoviesFlowRepository = get(named("source"))) }
    viewModel { MoviePagingRemoteFragmentViewModel(movieDatabase = get(), getMoviesFlowRepository = get(named("remote"))) }
    viewModel { StateSharedFlowFragmentViewModel() }
}