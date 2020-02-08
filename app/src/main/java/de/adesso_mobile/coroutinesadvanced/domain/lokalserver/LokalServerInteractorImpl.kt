package de.adesso_mobile.coroutinesadvanced.domain.lokalserver

import com.github.kittinunf.result.Result
import de.adesso_mobile.coroutinesadvanced.io.network.LokalServerService
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.receive
import io.ktor.utils.io.errors.IOException
import java.net.SocketTimeoutException

class LokalServerInteractorImpl(
    private val lokalServerService: LokalServerService
) : LokalServerInteractor {

    override suspend fun sendTestPost(value: String): Result<LokalServerService.LokalServerResponse, Exception> {
        return try {
            lokalServerService.sendTestPost(value)
                .run {
                    if (status.value == 200) {
                        Result.of(receive<LokalServerService.LokalServerResponse>())
                    } else {
                        Result.of { throw LokalServerException("Fehler", "Fehler beim Laden mit Statuscode ${status.value}") }
                    }
                }
            //catch exceptions from specific to general
        } catch (e: NoTransformationFoundException) {
            Result.of { throw LokalServerException("NoTransformationFoundException", e.message.toString()) }
        } catch (e: SocketTimeoutException) {
            Result.of { throw LokalServerException("SocketTimeoutException", e.message.toString()) }
        } catch (e: IOException) {
            Result.of { throw LokalServerException("IOException", e.message.toString()) }
        } catch (e: Exception) {
            Result.of { throw LokalServerException("Exception", e.message.toString()) }
        }
    }
}

data class LokalServerException(
    var exceptionType: String,
    override var message: String
) : Exception()
