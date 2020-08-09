package de.adesso_mobile.coroutinesadvanced.io.network.movies

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpStatement

class MovieService(val client: HttpClient, val baseUrl: String) {

    suspend fun fetchMovies(
        s: String,
        page: Int
    ) = client.get<HttpStatement>("http://www.omdbapi.com/?i=tt3896198&apikey=f360d074&s=$s&page=$page") {}.execute()
}