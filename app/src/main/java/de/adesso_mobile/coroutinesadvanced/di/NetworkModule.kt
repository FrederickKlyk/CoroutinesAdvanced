package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.common.HttpLoggingSensitiveInterceptor
import de.adesso_mobile.coroutinesadvanced.io.network.LokalServerService
import de.adesso_mobile.coroutinesadvanced.io.network.WeatherService
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.*
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber


fun networkModule(baseUrl: String) = module {
    // OkHttp Logging
    single {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }
    }
    // OkHttp Logging mit sensitiven Routen
    single { HttpLoggingSensitiveInterceptor(httpLoggingInterceptor = get()) }

    //Ktor Client
    single(named(DEFAULT_HTTP_CLIENT)) { provideHTTPClient(httpLoggingSensitiveInterceptor = get()) }
    single(named(MOCK_HTTP_CLIENT)) { provideMockHTTPClient() }

    // HTTP Services
    single { WeatherService(client = get(named(DEFAULT_HTTP_CLIENT)), baseUrl = baseUrl) }
    single { LokalServerService(client = get(named(DEFAULT_HTTP_CLIENT))) }
}

/**
 * Default Ktor Client mit [HttpLoggingSensitiveInterceptor].
 */
fun provideHTTPClient(httpLoggingSensitiveInterceptor: HttpLoggingSensitiveInterceptor) = HttpClient(engineFactory = OkHttp) {
    Charsets {
        register(Charsets.UTF_8)
    }
    defaultRequest {
        header("X-My-Header", "Header Wert")
    }
    engine {
        // OkHttp Logging
        addInterceptor(httpLoggingSensitiveInterceptor)
    }
    //JSON serialization
    install(JsonFeature) {
        serializer = GsonSerializer {
            disableHtmlEscaping()
        }
    }
    //Ktor Logging
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
    HttpResponseValidator {
        validateResponse { response ->
            val statusCode = response.status.value
            when (statusCode) {
                in 300..399 -> Timber.e(RedirectResponseException(response))
                in 400..499 -> Timber.e(ClientRequestException(response))
                in 500..599 -> Timber.e(ServerResponseException(response))
            }

            if (statusCode >= 600) {
                Timber.e(ResponseException(response))
            }
        }
    }
}

/**
 * Mock-Engine Ktor Client zum testen von Endpunkten.
 */
fun provideMockHTTPClient() = HttpClient(MockEngine){
    engine {
        addHandler { request ->
            Timber.d("REQUEST WAR: $request")
            when (request.url.fullUrl) {
                "https://example.org/" -> {
                    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Text.Plain.toString()))
                    respond("Hello, world", headers = responseHeaders)
                }
                else -> respondError(status = HttpStatusCode.NotFound, content = "Unhandled ${request.url.fullUrl}")
            }
        }
    }
    //Ktor Logging
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}

private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

/**
 * Namespace Flavors für HTTP-Clients.
 */
const val DEFAULT_HTTP_CLIENT = "default"
const val MOCK_HTTP_CLIENT = "mock"
