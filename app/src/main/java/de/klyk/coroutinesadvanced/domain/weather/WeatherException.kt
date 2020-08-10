package de.klyk.coroutinesadvanced.domain.weather

data class WeatherException(
    var exceptionType: String,
    override var message: String,
    var isHttpStatusCode: Boolean = false
) : Exception()