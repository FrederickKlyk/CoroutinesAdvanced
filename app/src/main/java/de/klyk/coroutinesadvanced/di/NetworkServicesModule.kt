package de.klyk.coroutinesadvanced.di

import de.klyk.coroutinesadvanced.io.network.movies.MovieService
import de.klyk.coroutinesadvanced.io.network.lokalserver.LokalServerService
import de.klyk.coroutinesadvanced.io.network.weather.WeatherService
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun networkServicesModule(baseUrl: String) = module {
    // HTTP Services
    single { WeatherService(client = get(named(DEFAULT_HTTP_CLIENT)), baseUrl = baseUrl) }
    single { MovieService(client = get(named(DEFAULT_HTTP_CLIENT)), baseUrl = BASE_URL) }
    single { LokalServerService(client = get(named(DEFAULT_HTTP_CLIENT))) }
}

/**
 * Namespace Flavors für HTTP-Clients.
 */
const val DEFAULT_HTTP_CLIENT = "default"
const val MOCK_HTTP_CLIENT = "mock"
const val BASE_URL = "http://www.omdbapi.com/?i=tt3896198&apikey=f360d074"