package de.adesso_mobile.coroutinesadvanced.domain.lokalserver

import com.github.kittinunf.result.Result
import de.adesso_mobile.coroutinesadvanced.io.network.LokalServerService
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.receive
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import java.net.SocketTimeoutException

class LokalServerInteractorImpl(
    private val lokalServerService: LokalServerService
) : LokalServerInteractor {

    override suspend fun sendTestPost(value: String): Result<LokalServerService.LokalServerResponse, LokalServerException> {
        return try {
            lokalServerService.sendTestPost(value)
                .run {
                    if (status.value == 200) {
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
