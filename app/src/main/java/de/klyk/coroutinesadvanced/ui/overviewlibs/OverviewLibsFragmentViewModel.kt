package de.klyk.coroutinesadvanced.ui.overviewlibs

import androidx.lifecycle.MutableLiveData
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel

class OverviewLibsFragmentViewModel : BaseViewModel() {
    val overviewLibsLiveData = MutableLiveData<String>().apply { value = "Placeholder Libs" }
}