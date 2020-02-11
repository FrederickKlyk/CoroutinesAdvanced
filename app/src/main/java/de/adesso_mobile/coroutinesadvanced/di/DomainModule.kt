package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.domain.lokalserver.LokalServerInteractor
import de.adesso_mobile.coroutinesadvanced.domain.lokalserver.LokalServerInteractorImpl
import de.adesso_mobile.coroutinesadvanced.domain.weather.WeatherInteractor
import de.adesso_mobile.coroutinesadvanced.domain.weather.WeatherInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    single<LokalServerInteractor> { LokalServerInteractorImpl(lokalServerService = get()) }
    single<WeatherInteractor> { WeatherInteractorImpl(weatherService = get()) }
}