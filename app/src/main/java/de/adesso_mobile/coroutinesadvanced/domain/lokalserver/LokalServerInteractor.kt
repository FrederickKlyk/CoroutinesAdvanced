package de.adesso_mobile.coroutinesadvanced.domain.lokalserver

import com.github.kittinunf.result.Result
import de.adesso_mobile.coroutinesadvanced.io.network.LokalServerService
import java.lang.Exception

interface LokalServerInteractor {
    suspend fun sendTestPost(value: String): Result<LokalServerService.LokalServerResponse, Exception>
}