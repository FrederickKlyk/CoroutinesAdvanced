package de.adesso_mobile.coroutinesadvanced.io.network.weather

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpStatement


class WeatherService(val client: HttpClient, val baseUrl: String) {

    suspend fun fetchWeather() =
        client.get<HttpStatement>("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").execute()
}