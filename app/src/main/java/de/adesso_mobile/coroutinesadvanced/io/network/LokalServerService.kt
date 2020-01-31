package de.adesso_mobile.coroutinesadvanced.io.network

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.statement.HttpStatement
import io.ktor.content.TextContent
import io.ktor.http.ContentType


class LokalServerService(val client: HttpClient) {
    
    suspend fun sendTestPost(value: String) = client.post<HttpStatement>(urlString = "http://10.0.2.2:8080/test") {
        body = TextContent(value, ContentType.Text.Plain)
    }.execute()

    data class LokalServerResponse(val response: String)
}