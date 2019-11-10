package de.adesso_mobile.coroutinesadvanced.io.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse


class WeatherService(val client: HttpClient, val baseUrl: String) {

    suspend fun fetchWeather() = client.get<HttpResponse>("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22"){
        
    }

    data class Weather(val coord: Coordinate, val base: String)

    data class Coordinate(val lon: Float, val lat: Float)
}