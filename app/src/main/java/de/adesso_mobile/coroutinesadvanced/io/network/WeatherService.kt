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

    val data: List<PartData> = formData {
        // Can append: String, Number, ByteArray and Input.
        append("hello", "world")
        append("number", 10)
        append("ba", byteArrayOf(1, 2, 3, 4))
        // Allow to set headers to the part:
        append("hello", "world", headersOf("key", "value"))
    }

    suspend fun sendTestPost() = client.post<HttpStatement>(urlString = "http://127.0.0.1:8080/test"){
        parameter("test", "lol")
    }

    suspend fun fetchWeather() = client.get<HttpStatement>("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").execute()

    data class Weather(val coord: Coordinate, val base: String, val main: Main, val wind:Wind)

    data class Coordinate(val lon: Float, val lat: Float)

    data class Main(val temp: Float, val pressure: Int, val humidity:Int, val temp_min: Float, val temp_max:Float)

    data class Wind(val speed: Float, val deg: Int)
}