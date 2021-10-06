package de.klyk.coroutinesadvanced.ui.main

import androidx.lifecycle.MutableLiveData
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel

class MainSharedViewModel : BaseViewModel() {

    val onBottomBarVisibleEvent = MutableLiveData<Boolean>(false)
}