package de.adesso_mobile.coroutinesadvanced

import android.app.Application
import de.adesso_mobile.coroutinesadvanced.di.commonModule
import de.adesso_mobile.coroutinesadvanced.di.networkModule
import de.adesso_mobile.coroutinesadvanced.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //Start Timber
        Timber.plant(Timber.DebugTree())
        // start Koin!
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(
                listOf(
                    viewModelModule,
                    commonModule,
                    networkModule(baseUrl = "https://samples.openweathermap.org")
                )
            )
        }
    }
}