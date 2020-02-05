package de.adesso_mobile.coroutinesadvanced.ui.viewpager2.common

import androidx.viewpager2.widget.ViewPager2

class OnPageChangeCallback(private val lastItem: Int, private val listener: (Int) -> Unit) : ViewPager2.OnPageChangeCallback() {

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)

        when (position) {
            0 -> listener.invoke(lastItem)
        }
    }
}