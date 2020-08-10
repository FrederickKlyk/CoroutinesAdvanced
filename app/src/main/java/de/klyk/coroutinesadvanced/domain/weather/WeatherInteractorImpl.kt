package de.klyk.coroutinesadvanced.domain.weather

import com.github.kittinunf.result.Result
import de.klyk.coroutinesadvanced.io.network.weather.WeatherService
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.receive
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException

class WeatherInteractorImpl(
    private val weatherService: WeatherService
) : WeatherInteractor {

    override suspend fun fetchWeather(): Result<Weather, WeatherException> {
        return try {
            weatherService.fetchWeather().run {
                if (status.value in 200..299) {
                    Result.success(receive())
                } else {
                    Result.error(
                        WeatherException(
                            exceptionType = status.value.toString(),
                            message = "Fehler beim Laden mit Statuscode ${status.value}",
                            isHttpStatusCode = true
                        )
                    )
                }
            }
        } catch (e: NoTransformationFoundException) {
            Result.error(WeatherException("NoTransformationFoundException", e.message.toString()))
        } catch (e: SocketTimeoutException) {
            Result.error(WeatherException("SocketTimeoutException", e.message.toString()))
        } catch (e: IOException) {
            Result.error(WeatherException("IOException", e.message.toString()))
        } catch (e: Exception) {
            Result.error(WeatherException("Exception", e.message.toString()))
        }
    }

    override fun fetchWeatherStream() = flow {
        // Fake data stream
        while (true) {
            delay(2000)
            // Send a random fake weather forecast data
            emit(
                Weather(
                    coord = Coordinate(10f, 20f),
                    base = "TestEcke",
                    main = Main(temp = (10..30).random().toFloat(), pressure = 1, humidity = 1, temp_min = 10f, temp_max = 30f),
                    wind = Wind(speed = (0..10).random().toFloat(), deg = 1)
                )
            )
        }
    }
}