package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.io.network.lokalserver.LokalServerService
import de.adesso_mobile.coroutinesadvanced.io.network.weather.WeatherService
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun networkServicesModule(baseUrl: String) = module {
    // HTTP Services
    single { WeatherService(client = get(named(DEFAULT_HTTP_CLIENT)), baseUrl = baseUrl) }
    single { LokalServerService(client = get(named(DEFAULT_HTTP_CLIENT))) }
}

/**
 * Namespace Flavors für HTTP-Clients.
 */
const val DEFAULT_HTTP_CLIENT = "default"
const val MOCK_HTTP_CLIENT = "mock"