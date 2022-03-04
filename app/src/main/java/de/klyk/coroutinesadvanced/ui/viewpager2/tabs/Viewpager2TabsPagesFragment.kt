package de.klyk.coroutinesadvanced.ui.viewpager2.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import de.klyk.coroutinesadvanced.databinding.TabContainerPagesFragmentBinding
import de.klyk.coroutinesadvanced.databinding.Viewpager2FragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import org.koin.android.ext.android.inject


class Viewpager2TabsPagesFragment : BaseFragment() {
    var binding : TabContainerPagesFragmentBinding? = null

    val viewModel: Viewpager2TabsPagesViewModel by inject()
    private val adapter = TabViewPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabContainerPagesFragmentBinding.inflate(inflater).apply {
            viewModel = this.viewModel
            lifecycleOwner = this@Viewpager2TabsPagesFragment
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
    }

    private fun setupTabLayout() {
        binding?.viewPagerForTabsDots?.adapter = adapter
        binding?.tabsDotsTL?.let {
            binding?.viewPagerForTabsDots?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    //Style von einzelnen Tabs
                }.attach()
            }
        }

        adapter.setItem(viewModel.categories.value)
    }
}
