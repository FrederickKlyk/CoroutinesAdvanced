package de.adesso_mobile.coroutinesadvanced.ui.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.adesso_mobile.coroutinesadvanced.databinding.Viewpager2FragmentBinding
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseFragment
import de.adesso_mobile.coroutinesadvanced.ui.viewpager2.common.CategoryAdapter
import de.adesso_mobile.coroutinesadvanced.ui.viewpager2.common.OnPageChangeCallback
import kotlinx.android.synthetic.main.viewpager2_fragment.*
import org.koin.android.ext.android.inject

class Viewpager2PageTransition : BaseFragment() {

    val viewModel: Viewpager2SharedViewModel by inject()
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }
    private lateinit var onPageChangeCallback: OnPageChangeCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return Viewpager2FragmentBinding.inflate(inflater).apply {
            viewModel = viewModel
            lifecycleOwner = this@Viewpager2PageTransition
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager2()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    private fun setupViewPager2() {
        viewPager2.adapter = adapter
        adapter.setItem(viewModel.categories.value)

        onPageChangeCallback = OnPageChangeCallback(viewModel.categories.value.size - 1) {
            viewPager2.currentItem = it
        }
        viewPager2.registerOnPageChangeCallback(onPageChangeCallback)
    }
}