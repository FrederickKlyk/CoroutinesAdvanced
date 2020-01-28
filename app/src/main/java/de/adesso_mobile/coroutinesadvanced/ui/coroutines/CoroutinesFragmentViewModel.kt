package de.adesso_mobile.coroutinesadvanced.ui.coroutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.io.network.LokalServerService
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import io.ktor.client.call.receive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

class CoroutinesFragmentViewModel(
    private val weatherApi: WeatherService,
    private val lokalServerService: LokalServerService
) : BaseViewModel() {

    val wetterDatenText = MutableLiveData<String>("")
    val postDataText = MutableLiveData("")
    val postDataResponseText = MutableLiveData("")

    fun initialize() = viewModelScope.launch {
        Timber.d("HTTP:")
        withContext(Dispatchers.IO) { weatherApi.fetchWeather() }.apply {
            if (status.value == 200) {
                Timber.d("${status.value}: ${status.description}")

                val weather = receive<WeatherService.Weather>()
                Timber.d(weather.toString())
                val p1 = weather.copy(base = "test")
                Timber.d(p1.toString())

                wetterDatenText.value = "Basis: ${weather.base} \n${weather.coord}\nTemperatur: ${String.format(
                    "%.2f",
                    weather.main.temp - 273.15
                )}°c \nWindstufe: ${weather.wind.speed}"
            } else {
                wetterDatenText.value = "Fehler beim Laden mit Statuscode ${status.value}"
            }
        }
    }

    fun sendHTTPPost() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try {
                lokalServerService.sendTestPost(postDataText.value ?: "")
                    .apply {
                        if (status.value == 200) {
                            postDataResponseText.value = receive<LokalServerService.lokalServerResponse>().response
                        } else {
                            postDataResponseText.value = "Fehler beim Laden mit Statuscode ${status.value}"
                        }
                    }
                //catch exceptions from specific to general
            } catch (e: SocketTimeoutException) {
                Timber.e("sendHTTPPost(), SocketTimeoutException: $e")
            } catch (e: IOException) {
                Timber.e("sendHTTPPost(), IOException: $e")
            } catch (e: Exception) {
                Timber.e("sendHTTPPost(), Exception: $e")
            }
        }
    }
}