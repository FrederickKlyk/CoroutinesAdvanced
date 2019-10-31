package de.adesso_mobile.coroutinesadvanced.ui.overviewlibs

import androidx.lifecycle.MutableLiveData
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel

class OverviewLibsFragmentViewModel : BaseViewModel() {
    val overviewLibsLiveData = MutableLiveData<String>().apply { value = "Placeholder Libs" }
}