package de.adesso_mobile.coroutinesadvanced.domain.weather

data class Weather(val coord: Coordinate, val base: String, val main: Main, val wind: Wind)

data class Coordinate(val lon: Float, val lat: Float)

data class Main(
    val temp: Float,
    val pressure: Int,
    val humidity: Int,
    val temp_min: Float,
    val temp_max: Float
)

data class Wind(val speed: Float, val deg: Int)