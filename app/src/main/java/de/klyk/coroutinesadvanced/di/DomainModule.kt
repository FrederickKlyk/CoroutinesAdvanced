package de.klyk.coroutinesadvanced.di

import de.klyk.coroutinesadvanced.domain.lokalserver.LokalServerInteractor
import de.klyk.coroutinesadvanced.domain.lokalserver.LokalServerInteractorImpl
import de.klyk.coroutinesadvanced.domain.weather.WeatherInteractor
import de.klyk.coroutinesadvanced.domain.weather.WeatherInteractorImpl
import de.klyk.coroutinesadvanced.ui.paging.MoviePagingSource
import org.koin.dsl.module

val domainModule = module {
    single<LokalServerInteractor> { LokalServerInteractorImpl(lokalServerService = get()) }
    single<WeatherInteractor> { WeatherInteractorImpl(weatherService = get()) }
    single{ MoviePagingSource(movieService =  get())}
}