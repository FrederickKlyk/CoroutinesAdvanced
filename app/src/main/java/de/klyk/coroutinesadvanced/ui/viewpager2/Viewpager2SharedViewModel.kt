package de.klyk.coroutinesadvanced.ui.viewpager2

import de.klyk.coroutinesadvanced.domain.viewpager2.Category
import de.klyk.coroutinesadvanced.ui.base.BaseViewModel
import de.klyk.coroutinesadvanced.utils.MutableLiveDataNotNull

class Viewpager2SharedViewModel : BaseViewModel() {

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