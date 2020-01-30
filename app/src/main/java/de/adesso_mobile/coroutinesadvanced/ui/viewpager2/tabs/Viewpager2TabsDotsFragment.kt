package de.adesso_mobile.coroutinesadvanced.ui.viewpager2.tabs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import de.adesso_mobile.coroutinesadvanced.databinding.TabContainerDotsFragmentBinding
import de.adesso_mobile.coroutinesadvanced.databinding.TabContainerFragmentBinding
import de.adesso_mobile.coroutinesadvanced.ui.main.tabs.TabContainerViewModel
import kotlinx.android.synthetic.main.tab_container_dots_fragment.view.*
import kotlinx.android.synthetic.main.tab_container_fragment.view.*
import org.koin.android.ext.android.inject


class Viewpager2TabsDotsFragment : Fragment() {

    val viewModel: TabContainerViewModel by inject()
    private val tabViewPagerAdapter = TabViewPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = TabContainerDotsFragmentBinding.inflate(inflater).apply {
            viewModel = viewModel
            lifecycleOwner = this@Viewpager2TabsDotsFragment
        }.root

        v.viewPagerForTabsDots.adapter = tabViewPagerAdapter
        TabLayoutMediator(v.tabsDots_TL, v.viewPagerForTabsDots){ tab, position ->
            //Style von einzelnen Tabs
        }.attach()

        return v
    }
}
