package de.adesso_mobile.coroutinesadvanced.ui.viewpager2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import de.adesso_mobile.coroutinesadvanced.databinding.Viewpager2FragmentBinding
import kotlinx.android.synthetic.main.viewpager2_fragment.*
import org.koin.android.ext.android.inject

class Viewpager2HorizontalFragment : Fragment() {

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
