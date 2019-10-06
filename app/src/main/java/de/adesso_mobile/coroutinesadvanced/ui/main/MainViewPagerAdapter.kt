package de.adesso_mobile.coroutinesadvanced.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DummyFragment()
            1 -> DummyFragment()
            2 -> DummyFragment()
            else -> DummyFragment()
        }
    }

    override fun getItemCount() = 3
}