package de.adesso_mobile.coroutinesadvanced.ui.coroutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.domain.lokalserver.LokalServerInteractor
import de.adesso_mobile.coroutinesadvanced.domain.weather.WeatherInteractor
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutinesFragmentViewModel(
    private val weatherInteractor: WeatherInteractor,
    private val lokalServerInteractor: LokalServerInteractor
) : BaseViewModel() {

    val wetterDatenText = MutableLiveData<String>("")
    val postDataText = MutableLiveData("")
    val postDataResponseText = MutableLiveData("")

    fun initialize() = viewModelScope.launch {
        withContext(Dispatchers.IO) { weatherInteractor.fetchWeather() }
            .fold({ weather ->
                wetterDatenText.value = "Basis: ${weather.base} \n${weather.coord}\nTemperatur: ${String.format(
                    "%.2f",
                    weather.main.temp - 273.15
                )}°c \nWindstufe: ${weather.wind.speed}"
            }, {
                if (it.isHttpStatusCode)
                    wetterDatenText.value = "Fehler beim Laden mit Statuscode ${it.exceptionType}"
                else
                    wetterDatenText.value = "${it.exceptionType}\n Exception Mesage:\n ${it.message}"
            })
    }

    fun sendHTTPPost() = viewModelScope.launch {
        withContext(Dispatchers.IO) { lokalServerInteractor.sendTestPost(postDataText.value ?: "") }
            .fold({
                postDataResponseText.setValue(it.response)
            }, {
                if (it.isHttpStatusCode)
                    postDataResponseText.setValue("Fehler beim Laden mit Statuscode ${it.exceptionType}")
                else
                    postDataResponseText.setValue("${it.exceptionType}\n Exception Mesage:\n ${it.message}")
            })
    }
}