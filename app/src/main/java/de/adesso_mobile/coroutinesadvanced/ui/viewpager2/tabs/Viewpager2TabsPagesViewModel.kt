package de.adesso_mobile.coroutinesadvanced.ui.viewpager2.tabs

import de.adesso_mobile.coroutinesadvanced.domain.viewpager2.Category
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseViewModel
import de.adesso_mobile.coroutinesadvanced.utils.MutableLiveDataNotNull

class Viewpager2TabsPagesViewModel : BaseViewModel() {
    val categories = MutableLiveDataNotNull(
        listOf(
            Category(1, "Your Recording"),
            Category(2, "Film"),
            Category(3, "Series"),
            Category(4, "Kids"),
            Category(5, "Sport")
        )
    )
}