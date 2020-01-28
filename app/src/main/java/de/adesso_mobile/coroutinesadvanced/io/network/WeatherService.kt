package de.adesso_mobile.coroutinesadvanced.io.network

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpStatement
import io.ktor.http.Parameters
import io.ktor.http.ParametersBuilder
import io.ktor.http.content.PartData
import io.ktor.http.headersOf


class WeatherService(val client: HttpClient, val baseUrl: String) {

    suspend fun fetchWeather() = client.get<HttpStatement>("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").execute()

    data class Weather(val coord: Coordinate, val base: String, val main: Main, val wind:Wind)

    data class Coordinate(val lon: Float, val lat: Float)

    data class Main(val temp: Float, val pressure: Int, val humidity:Int, val temp_min: Float, val temp_max:Float)

    data class Wind(val speed: Float, val deg: Int)
}