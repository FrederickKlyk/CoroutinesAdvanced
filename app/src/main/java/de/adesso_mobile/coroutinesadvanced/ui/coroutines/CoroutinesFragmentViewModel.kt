package de.adesso_mobile.coroutinesadvanced.ui.coroutines

import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import io.ktor.client.call.receive
import kotlinx.coroutines.*
import timber.log.Timber

class CoroutinesFragmentViewModel(private val weatherApi: WeatherService) : BaseViewModel() {


    fun initialize() = viewModelScope.launch {
        Timber.d("HTTP:")
        try {
            withContext(Dispatchers.IO) { weatherApi.fetchWeather() }.apply {
                if (status.value == 200) {
                    Timber.d(status.description)
                    Timber.d(status.value.toString())
                }
                val weather = receive<WeatherService.Weather>()
                Timber.d(weather.toString())
                val p1 = weather.copy(base = "test")
                Timber.d(p1.toString())
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}