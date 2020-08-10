package de.klyk.coroutinesadvanced.ui.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import de.klyk.coroutinesadvanced.databinding.Viewpager2FragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import de.klyk.coroutinesadvanced.ui.viewpager2.common.TabLayoutOnPageChangeCallback
import de.klyk.coroutinesadvanced.ui.viewpager2.common.ViewPager2PageTransformation
import kotlinx.android.synthetic.main.viewpager2_fragment.*
import org.koin.android.ext.android.inject
import kotlin.math.abs

class Viewpager2PageTransition : BaseFragment() {

    val viewModel: Viewpager2SharedViewModel by inject()
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }
    private lateinit var tabLayoutOnPageChangeCallback: TabLayoutOnPageChangeCallback

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

    private fun setupViewPager2() {
        viewPager2.adapter = adapter
        adapter.setItem(viewModel.categories.value)

        // Springt auf die letzte Page, wenn die erste Page ausgewählt wird.
        tabLayoutOnPageChangeCallback = TabLayoutOnPageChangeCallback(lastItem = viewModel.categories.value.size - 1) {
            viewPager2.currentItem = it
        }

        // Abstände zwischen den Pages hinzufügen
        val marginPageTransformer = MarginPageTransformer(50)

        viewPager2.registerOnPageChangeCallback(tabLayoutOnPageChangeCallback)
        viewPager2.setPageTransformer(CompositePageTransformer().apply {
            addTransformer(ViewPager2PageTransformation())
            addTransformer(translationPageTransformer())
            addTransformer(marginPageTransformer)
        })
    }

    // Translation der X und Y Achse
    private fun translationPageTransformer() =
        ViewPager2.PageTransformer { page, position ->
            val absPos = abs(position)
            page.apply {
                translationY = absPos * 500f
                translationX = absPos * 500f
                scaleX = 1f
                scaleY = 1f
            }
        }
}