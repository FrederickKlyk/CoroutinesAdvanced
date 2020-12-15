package de.klyk.coroutinesadvanced.ui.main.coroutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateSharedFlowFragmentViewModel : BaseViewModel() {
    val nameLiveData = MutableLiveData("")
    val nameLiveData2 = MutableLiveData("Der neue Datentyp")
    val sharedFlowText = MutableLiveData("")

    private val _myStateFlowCounter = MutableStateFlow(0)
    val myStateFlowCounter: StateFlow<Int> = _myStateFlowCounter
    val nameStateFlow = MutableStateFlow("")

    private val _nameSharedFlow = MutableSharedFlow<String>(onBufferOverflow = BufferOverflow.DROP_LATEST, replay = 0, extraBufferCapacity = 1)
    val nameSharedFlow: SharedFlow<String> = _nameSharedFlow

    /** StateFlow*/
    fun onStateFlowButtonEmitClicked() {
        nameStateFlow.value = nameLiveData.value ?: ""
    }

    fun incrementStateFlowCounter() {
        _myStateFlowCounter.value++
    }

    /** SharedFlow*/
    fun onSharedFlowButtonEmitClicked() {
        _nameSharedFlow.tryEmit(nameLiveData2.value ?: "")
        nameLiveData2.value = ""
    }

    fun onSharedFlowButtonSubscribe() {
        viewModelScope.launch {
            nameSharedFlow.collect {
                sharedFlowText.value += "$it --> "
            }
        }
    }
}