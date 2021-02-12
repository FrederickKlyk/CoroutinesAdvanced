package de.klyk.coroutinesadvanced.ui.main.coroutines

import androidx.lifecycle.viewModelScope
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateFlowFragmentViewModel : BaseViewModel() {

    private val _names = MutableStateFlow<String?>(null)
    val names: StateFlow<String?> = _names
    val newName = MutableStateFlow<String?>(null)


    fun loadMyFriends() {
        viewModelScope.launch {
            try {
                repoLoadFriends()
                    .combine(newName) { namesFromRepo, addedName ->
                        if (addedName.isNullOrEmpty()) namesFromRepo else "$namesFromRepo, $addedName"
                    }
                    .flowOn(Dispatchers.IO)
                    .collect {
                        _names.value = it
                    }
            } catch (ex: Exception) {
                _names.value = "repo is not available"
            }
        }
    }

    private fun repoLoadFriends(): Flow<String> = flow {
        emit(listOf("Fred", "Pit", "Kurt").joinToString())
    }
}