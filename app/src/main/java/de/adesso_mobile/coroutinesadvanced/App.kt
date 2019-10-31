package de.adesso_mobile.coroutinesadvanced

import android.app.Application
import de.adesso_mobile.coroutinesadvanced.di.commonModule
import de.adesso_mobile.coroutinesadvanced.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(listOf(
                viewModelModule,
                commonModule
            ))
        }
    }
}