package de.adesso_mobile.coroutinesadvanced.ui.coroutines

import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CoroutinesFragmentViewModel : BaseViewModel() {

    fun initialize() = viewModelScope.launch {
        val weatherApi = WeatherService()

        Timber.d("HTTP:")
        try {
            val result = withContext(Dispatchers.IO) { weatherApi.fetchWeather() }
            Timber.d(result)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

}