package de.adesso_mobile.coroutinesadvanced.ui.coroutines

import androidx.lifecycle.viewModelScope
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class CoroutinesFragmentViewModel : BaseViewModel() {

    fun initialize() = viewModelScope.launch {

    }
}