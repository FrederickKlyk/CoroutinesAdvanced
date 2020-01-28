package de.adesso_mobile.coroutinesadvanced.common

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class HttpLoggingIntecept : Interceptor {
    val logging = HttpLoggingInterceptor()

    init {
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BASIC }
    }

    override fun intercept(chain: Interceptor.Chain): Response = when {
        chain.request().url.toString() == "geheim" -> {
            chain.proceed(chain.request())
        }
        else -> {
            Timber.d("URL ist nicht sensitiv")
            logging.intercept(chain)
        }
    }
}