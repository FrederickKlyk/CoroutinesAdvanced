package de.adesso_mobile.coroutinesadvanced.ui.main.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.domain.weather.WeatherInteractor
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import de.adesso_mobile.coroutinesadvanced.utils.MutableLiveDataNotNull
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readBytes
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FlowFragmentViewModel(
    weatherInteractor: WeatherInteractor,
    private val websocketClient: HttpClient
) : BaseViewModel() {

    val wsChatMessage = MutableLiveData("")
    val wsResponse = MutableLiveDataNotNull("")

    private val _weatherForcast = weatherInteractor
        .fetchWeatherStream()
        .map {
            delay(1000)

            "Die Temperatur beträgt: ${it.main.temp}°c\nDer Wind hat die Stärke: ${it.wind.speed}"
        }
        .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)
    val weatherForcastString: LiveData<String>
        get() = _weatherForcast


    fun sendChatMessage() {
        viewModelScope.launch {
            websocketClient.ws(
                method = HttpMethod.Get,
                host = "10.0.2.2",
                port = 8080, path = "/chat"
            ) {
                // Send text frame.
                send(Frame.Text(wsChatMessage.value ?: ""))

                // Receive frame.
                when (val frame = incoming.receive()) {
                    is Frame.Text -> {
                        println(frame.readText())
                        wsResponse.value = frame.readText()
                    }
                    is Frame.Binary -> println(frame.readBytes())
                }
            }
        }
    }
}