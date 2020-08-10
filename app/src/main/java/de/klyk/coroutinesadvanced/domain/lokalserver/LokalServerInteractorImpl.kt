package de.klyk.coroutinesadvanced.domain.lokalserver

import com.github.kittinunf.result.Result
import de.klyk.coroutinesadvanced.io.network.lokalserver.LokalServerService
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.receive
import io.ktor.utils.io.errors.IOException
import java.net.SocketTimeoutException

class LokalServerInteractorImpl(
    private val lokalServerService: LokalServerService
) : LokalServerInteractor {

    override suspend fun sendTestPost(value: String): Result<LokalServerResponse, LokalServerException> {
        return try {
            lokalServerService.sendTestPost(value)
                .run {
                    if (status.value in 200..299) {
                        Result.success(receive())
                    } else {
                        Result.error(
                            LokalServerException(
                                exceptionType = status.value.toString(),
                                message = "Fehler beim Laden mit Statuscode ${status.value}",
                                isHttpStatusCode = true
                            )
                        )
                    }
                }
            //catch exceptions from specific to general
        } catch (e: NoTransformationFoundException) {
            Result.error(LokalServerException("NoTransformationFoundException", e.message.toString()))
        } catch (e: SocketTimeoutException) {
            Result.error(LokalServerException("SocketTimeoutException", e.message.toString()))
        } catch (e: IOException) {
            Result.error(LokalServerException("IOException", e.message.toString()))
        } catch (e: Exception) {
            Result.error(LokalServerException("Exception", e.message.toString()))
        }
    }
}

data class LokalServerException(
    var exceptionType: String,
    override var message: String,
    var isHttpStatusCode: Boolean = false
) : Exception()

data class LokalServerResponse(val response: String)