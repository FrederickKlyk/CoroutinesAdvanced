package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.common.HttpLoggingIntecept
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import org.koin.dsl.module


fun networkModule(baseUrl: String) = module {
    single {
        HttpClient(OkHttp) {

            engine {
                config {
                    followRedirects(true)
                }
                // OkHttp Logging
                addInterceptor(HttpLoggingIntecept())
            }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            //Ktor Logging
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    single { WeatherService(client = get(), baseUrl = baseUrl)}
}