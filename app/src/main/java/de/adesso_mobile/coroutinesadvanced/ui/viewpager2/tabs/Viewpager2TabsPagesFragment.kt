package de.adesso_mobile.coroutinesadvanced.ui.viewpager2.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import de.adesso_mobile.coroutinesadvanced.databinding.TabContainerPagesFragmentBinding
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseFragment
import kotlinx.android.synthetic.main.tab_container_pages_fragment.*
import org.koin.android.ext.android.inject


class Viewpager2TabsPagesFragment : BaseFragment() {

    val viewModel: Viewpager2TabsPagesViewModel by inject()
    private val adapter = TabViewPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TabContainerPagesFragmentBinding.inflate(inflater).apply {
            viewModel = this.viewModel
            lifecycleOwner = this@Viewpager2TabsPagesFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
    }

    private fun setupTabLayout() {
        viewPagerForTabsDots.adapter = adapter
        TabLayoutMediator(tabsDots_TL, viewPagerForTabsDots) { tab, position ->
            //Style von einzelnen Tabs
        }.attach()

        adapter.setItem(viewModel.categories.value)
    }
}
