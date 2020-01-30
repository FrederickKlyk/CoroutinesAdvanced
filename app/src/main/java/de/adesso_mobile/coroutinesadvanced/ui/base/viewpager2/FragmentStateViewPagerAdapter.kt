package de.adesso_mobile.coroutinesadvanced.ui.base.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import de.adesso_mobile.coroutinesadvanced.ui.main.DummyFragment

class FragmentStateViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()
    private val fragmentTitleList: ArrayList<String> = ArrayList()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    fun getPageTitle(position: Int): CharSequence? = fragmentTitleList[position]

    override fun createFragment(position: Int): Fragment {
        return if (position in 0..fragmentList.size) {
            fragmentList[position]
        } else {
            DummyFragment()
        }
    }

    override fun getItemCount() = fragmentList.size
}