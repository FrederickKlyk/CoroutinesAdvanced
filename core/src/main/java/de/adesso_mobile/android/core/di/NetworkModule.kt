package de.adesso_mobile.android.core.di

import de.adesso_mobile.android.core.common.HttpLoggingSensitiveInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.*
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import io.ktor.utils.io.errors.IOException
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber
import java.net.SocketTimeoutException

val networkModule = module {
    // OkHttp Logging
    single {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }
    }
    // OkHttp Logging mit sensitiven Routen
    single { HttpLoggingSensitiveInterceptor(httpLoggingInterceptor = get()) }

    //Ktor Clients
    single(named(DEFAULT_HTTP_CLIENT)) { provideHTTPClient(httpLoggingSensitiveInterceptor = get()) }
    single(named(MOCK_HTTP_CLIENT)) { provideMockHTTPClient() }
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
    //Ktor Websockets
    install(WebSockets)
    HttpResponseValidator {
        validateResponse { response: HttpResponse ->
            val statusCode = response.status.value
            Timber.d("Statuscode: $statusCode")
            when (statusCode) {
                in 300..399 -> Timber.e(RedirectResponseException(response))
                in 400..499 -> Timber.e(ClientRequestException(response))
                in 500..599 -> Timber.e(ServerResponseException(response))
            }

            if (statusCode >= 600) {
                Timber.e(ResponseException(response))
            }
        }

        handleResponseException { cause: Throwable ->
            //catch exceptions from specific to general
            when (cause) {
                is NoTransformationFoundException -> {
                    Timber.e("sendHTTPPost(), NoTransformationFoundException: $cause")
                }
                is SocketTimeoutException -> {
                    Timber.e("sendHTTPPost(), SocketTimeoutException: $cause")
                }
                is IOException -> {
                    Timber.e("sendHTTPPost(), IOException: $cause")
                }
                is Exception -> {
                    Timber.e("sendHTTPPost(), Exception: $cause")
                }
            }
        }
    }
}

/**
 * Mock-Engine Ktor Client zum testen von Endpunkten.
 */
fun provideMockHTTPClient() = HttpClient(MockEngine) {
    engine {
        addHandler { request ->
            Timber.d("REQUEST WAR: $request")
            when (request.url.fullUrl) {
                "https://example.org/" -> {
                    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Text.Plain.toString()))
                    respond("Hello, world", headers = responseHeaders)
                }
                "http://10.0.2.2:8080/test" -> {
                    respond(content = "{\"response\": \"Ich bin ein Mock\"}", status = HttpStatusCode.OK, headers = Headers.build {
                        this["Content-Type"] = "application/json"
                    })
                }
                else -> respond(status = HttpStatusCode.NotFound, content = "URL Konnte nicht gefunden werden.")
            }
        }
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
}

private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

/**
 * Namespace Flavors für HTTP-Clients.
 */
const val DEFAULT_HTTP_CLIENT = "default"
const val MOCK_HTTP_CLIENT = "mock"