package de.adesso_mobile.coroutinesadvanced.ui.coroutines

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import io.ktor.client.call.receive
import io.ktor.client.response.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CoroutinesFragmentViewModel : BaseViewModel() {
    private val weatherApi = WeatherService()

    fun initialize() = viewModelScope.launch {

        Timber.d("HTTP:")
        try {
            val result = withContext(Dispatchers.IO) { weatherApi.fetchWeather() }

            if(result.status.value == 200){
                Timber.d(result.status.description)
                Timber.d(result.status.value.toString())
            }
            val p1= result.receive<WeatherService.Weather>()
            Timber.d(p1.toString())
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}