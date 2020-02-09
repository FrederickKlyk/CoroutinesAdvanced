package de.adesso_mobile.coroutinesadvanced.domain.weather

import com.github.kittinunf.result.Result

interface WeatherInteractor {
    suspend fun fetchWeather(): Result<Weather, WeatherException>
}