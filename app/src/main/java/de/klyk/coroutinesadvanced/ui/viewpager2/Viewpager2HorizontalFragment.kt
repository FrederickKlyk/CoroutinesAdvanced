package de.klyk.coroutinesadvanced.ui.viewpager2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import de.klyk.coroutinesadvanced.databinding.Viewpager2FragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import kotlinx.android.synthetic.main.viewpager2_fragment.*
import org.koin.android.ext.android.inject

class Viewpager2HorizontalFragment : BaseFragment() {

    val viewModel: Viewpager2SharedViewModel by inject()
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return Viewpager2FragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@Viewpager2HorizontalFragment
            viewModel = viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        adapter.setItem(viewModel.categories.value)
    }
}
