package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.ui.main.MainActivityViewModel
import de.adesso_mobile.coroutinesadvanced.ui.main.TabContainerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { TabContainerViewModel() }
}