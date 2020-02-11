package de.adesso_mobile.coroutinesadvanced.domain.lokalserver

import com.github.kittinunf.result.Result

interface LokalServerInteractor {
    suspend fun sendTestPost(value: String): Result<LokalServerResponse, LokalServerException>
}