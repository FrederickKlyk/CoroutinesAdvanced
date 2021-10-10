package de.klyk.dummy.di

import de.klyk.dummy.ui.MainDummyFragmentViewModel
import de.klyk.dummy.ui.SecondDummyFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel{ MainDummyFragmentViewModel() }
    viewModel{ SecondDummyFragmentViewModel() }
}