package de.klyk.coroutinesadvanced.domain.weather

import com.github.kittinunf.result.Result
import kotlinx.coroutines.flow.Flow

interface WeatherInteractor {
    suspend fun fetchWeather(): Result<Weather, WeatherException>
    fun fetchWeatherStream() : Flow<Weather>
}