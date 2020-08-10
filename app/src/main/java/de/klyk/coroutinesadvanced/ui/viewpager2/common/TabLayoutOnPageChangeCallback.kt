package de.klyk.coroutinesadvanced.ui.viewpager2.common

import androidx.viewpager2.widget.ViewPager2

/**
 * onPageSelected: This method will be invoked when a new page becomes selected
 * onPageScrollStateChange: Called when the scroll state changes
 * onPageScrolled: This method will be invoked when the current page is scrolled
 */
class TabLayoutOnPageChangeCallback(private val lastItem: Int, private val listener: (Int) -> Unit) : ViewPager2.OnPageChangeCallback() {

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)

        when (position) {
            0 -> listener.invoke(lastItem)
        }
    }
}