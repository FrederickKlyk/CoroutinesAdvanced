package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.ui.list.ListFragmentViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.MainActivityViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.coroutines.CoroutinesFragmentViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.coroutines.FlowFragmentViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.coroutines.WebsocketsFragmentViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.tabs.TabContainerViewModel
import de.adesso_mobile.coroutinesadvanced.ui.overviewlibs.OverviewLibsFragmentViewModel
import de.adesso_mobile.coroutinesadvanced.ui.paging.MoviePagingFragmentViewModel
import de.adesso_mobile.coroutinesadvanced.ui.viewpager2.Viewpager2SharedViewModel
import de.adesso_mobile.coroutinesadvanced.ui.viewpager2.tabs.Viewpager2TabsPagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { TabContainerViewModel() }
    viewModel {
        CoroutinesFragmentViewModel(weatherInteractor = get(), lokalServerInteractor = get())
    }
    viewModel { OverviewLibsFragmentViewModel() }
    viewModel { Viewpager2SharedViewModel() }
    viewModel { Viewpager2TabsPagesViewModel() }
    viewModel { FlowFragmentViewModel(weatherInteractor = get()) }
    viewModel { WebsocketsFragmentViewModel(websocketClient = get(named(DEFAULT_HTTP_CLIENT))) }
    viewModel { ListFragmentViewModel() }
    viewModel { MoviePagingFragmentViewModel(moviePagingSource = get(), movieService = get(), movieDatabase = get(), movieDao = get() ) }
}