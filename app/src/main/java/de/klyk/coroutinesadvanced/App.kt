package de.klyk.coroutinesadvanced

import android.app.Application
import de.klyk.android.core.di.networkModule
import de.klyk.coroutinesadvanced.di.*
import de.klyk.dummy.di.viewModelModule as dummyViewModelModule
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
                    databaseModule,
                    networkModule,
                    networkServicesModule(baseUrl = "https://samples.openweathermap.org"),
                    domainModule,
                   dummyViewModelModule
                )
            )
        }
    }
}