package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.ui.coroutines.CoroutinesFragmentViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.MainActivityViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.TabContainerViewModel
import de.adesso_mobile.coroutinesadvanced.ui.overviewlibs.OverviewLibsFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { TabContainerViewModel() }
    viewModel { CoroutinesFragmentViewModel() }
    viewModel { OverviewLibsFragmentViewModel() }
}