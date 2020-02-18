package de.adesso_mobile.coroutinesadvanced.ui.main.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.domain.weather.WeatherInteractor
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class FlowFragmentViewModel(
    weatherInteractor: WeatherInteractor
) : BaseViewModel() {

    private val _weatherForcast = weatherInteractor
        .fetchWeatherStream()
        .distinctUntilChanged()
        .map {
            delay(1000)

            "Die Temperatur beträgt: ${it.main.temp}°c\nDer Wind hat die Stärke: ${it.wind.speed}"
        }
        .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)

    val weatherForcastString: LiveData<String>
        get() = _weatherForcast
}