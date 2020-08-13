package de.klyk.coroutinesadvanced.di

import de.klyk.coroutinesadvanced.domain.lokalserver.LokalServerInteractor
import de.klyk.coroutinesadvanced.domain.lokalserver.LokalServerInteractorImpl
import de.klyk.coroutinesadvanced.domain.weather.WeatherInteractor
import de.klyk.coroutinesadvanced.domain.weather.WeatherInteractorImpl
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesFlowRepository
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesRemoteFlowRepositoryImpl
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesSourceFlowRepositoryImpl
import de.klyk.coroutinesadvanced.io.repository.movie.MoviePagingSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    single<LokalServerInteractor> { LokalServerInteractorImpl(lokalServerService = get()) }
    single<WeatherInteractor> { WeatherInteractorImpl(weatherService = get()) }
    single{ MoviePagingSource(movieService = get()) }
    single<GetMoviesFlowRepository>(named("source")) { GetMoviesSourceFlowRepositoryImpl(moviePagingSource = get()) }
}