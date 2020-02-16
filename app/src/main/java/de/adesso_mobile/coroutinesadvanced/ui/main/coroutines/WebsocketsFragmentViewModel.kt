package de.adesso_mobile.coroutinesadvanced.ui.main.coroutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import de.adesso_mobile.coroutinesadvanced.utils.MutableLiveDataNotNull
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readBytes
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.channels.mapNotNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class WebsocketsFragmentViewModel(
    private val websocketClient: HttpClient
) : BaseViewModel() {

    val wsChatMessage = MutableLiveData("")
    val wsResponse = MutableLiveDataNotNull("")

    fun sendChatMessage() {
        viewModelScope.launch {
            websocketClient.ws(
                method = HttpMethod.Get,
                host = "10.0.2.2",
                port = 8080, path = "/chat"
            ) {
                // Send text frame.
                send(Frame.Text(wsChatMessage.value ?: ""))
                wsChatMessage.value = ""

                // Receive frame.
                incoming.consumeAsFlow().collect { frame ->
                    when (frame) {
                        is Frame.Text -> {
                            println(frame.readText())
                            wsResponse.value = frame.readText()
                        }
                        is Frame.Binary -> println(frame.readBytes())
                    }
                }
            }
        }
    }
}