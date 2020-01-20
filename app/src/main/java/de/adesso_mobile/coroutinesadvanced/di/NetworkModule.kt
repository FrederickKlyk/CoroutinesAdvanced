package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.common.HttpLoggingIntecept
import de.adesso_mobile.coroutinesadvanced.io.network.LokalServerService
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.*
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.header
import org.koin.dsl.module


fun networkModule(baseUrl: String) = module {
    single {
        HttpClient(OkHttp) {
            Charsets {
                register(Charsets.UTF_8)
            }
            defaultRequest {
                header("X-My-Header", "Header Wert")
            }
            engine {
                // OkHttp Logging
                addInterceptor(HttpLoggingIntecept())
            }
            install(JsonFeature) {
                serializer = GsonSerializer{
                    disableHtmlEscaping()
                }
            }
            //Ktor Logging
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            HttpResponseValidator {
                validateResponse { response ->
                    val statusCode = response.status.value
                    when (statusCode) {
                        in 300..399 -> throw RedirectResponseException(response)
                        in 400..499 -> throw ClientRequestException(response)
                        in 500..599 -> throw ServerResponseException(response)
                    }

                    if (statusCode >= 600) {
                        throw ResponseException(response)
                    }
                }
            }
        }
    }

    single { WeatherService(client = get(), baseUrl = baseUrl) }
    single { LokalServerService(client = get()) }
}