package de.adesso_mobile.coroutinesadvanced

import android.app.Application
import de.adesso_mobile.coroutinesadvanced.di.commonModule
import de.adesso_mobile.coroutinesadvanced.di.domainModule
import de.adesso_mobile.coroutinesadvanced.di.networkServicesModule
import de.adesso_mobile.coroutinesadvanced.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import de.adesso_mobile.android.core.di.networkModule


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
                    networkModule,
                    networkServicesModule(baseUrl = "https://samples.openweathermap.org"),
                    domainModule
                )
            )
        }
    }
}