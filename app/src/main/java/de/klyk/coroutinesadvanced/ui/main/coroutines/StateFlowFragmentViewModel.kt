package de.klyk.coroutinesadvanced.ui.main.coroutines

import androidx.lifecycle.viewModelScope
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateFlowFragmentViewModel : BaseViewModel() {

    private val _initLabelValue = MutableStateFlow("")
    val initLabelValue: StateFlow<String> = _initLabelValue

    private val _names = MutableStateFlow<String?>(null)
    val names: StateFlow<String?> = _names

    val newName = MutableStateFlow<String?>("")
    val repoFriends = mutableListOf("Fred", "Pit", "Kurt")

    init {
        _initLabelValue.value = "Test von One- und Two-Way-Bindings"
    }

    fun loadMyFriends() {
        viewModelScope.launch {
            try {
                repoLoadFriends() // Will be executed in IO
                    .combine(newName) { namesFromRepo, addedName ->
                        if (addedName.isNullOrEmpty()) "$namesFromRepo, ohne neuen Freund" else "$namesFromRepo, $addedName"
                    } // Will be executed in IO
                    .flowOn(Dispatchers.IO)
                    .collect {
                        _names.value = it
                    }// Will be executed in the Main
            } catch (ex: Exception) {
                _names.value = "repo is not available"
            }
        }
    }

    fun addRepoFriend(friend:String){
        repoFriends.add(friend)
    }

    //Mock async Repo, which should be called from I/O-Thread
    private fun repoLoadFriends(): Flow<String> = flow {
        emit(repoFriends.joinToString())
    }
}