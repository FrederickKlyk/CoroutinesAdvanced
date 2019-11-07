package de.adesso_mobile.coroutinesadvanced.ui.coroutines

import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CoroutinesFragmentViewModel : BaseViewModel() {
    val weatherApi = WeatherService()

    fun initialize() = viewModelScope.launch {

        Timber.d("HTTP:")
        try {
            val result = withContext(Dispatchers.IO) { weatherApi.fetchWeather() }
            Timber.d(result.toString())
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}