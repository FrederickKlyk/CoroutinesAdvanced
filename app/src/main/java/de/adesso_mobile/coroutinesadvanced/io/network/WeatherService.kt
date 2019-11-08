package de.adesso_mobile.coroutinesadvanced.io.network

import de.adesso_mobile.coroutinesadvanced.common.HttpLoggingIntecept
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse


class WeatherService {
    private val client = HttpClient(OkHttp) {

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

    suspend fun fetchWeather() = client.get<HttpResponse> {
        url("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
    }

    companion object {
        private const val baseUrl = "https://samples.openweathermap.org"
    }

    data class Weather(val coord: Coordinate, val base: String)

    data class Coordinate(val lon: Float, val lat: Float)
}