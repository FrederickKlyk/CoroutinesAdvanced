package de.klyk.coroutinesadvanced.ui.viewpager2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import de.klyk.coroutinesadvanced.databinding.Viewpager2FragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import org.koin.android.ext.android.inject

class Viewpager2VerticalFragment : BaseFragment() {
    var binding : Viewpager2FragmentBinding? = null

    val viewModel: Viewpager2SharedViewModel by inject()
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Viewpager2FragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@Viewpager2VerticalFragment
            viewModel = viewModel
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        binding?.viewPager2?.adapter = adapter
        binding?.viewPager2?.orientation = ViewPager2.ORIENTATION_VERTICAL
        adapter.setItem(viewModel.categories.value)
    }
}
