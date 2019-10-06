package de.adesso_mobile.coroutinesadvanced.ui.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import de.adesso_mobile.coroutinesadvanced.databinding.TabContainerFragmentBinding
import kotlinx.android.synthetic.main.tab_container_fragment.*
import org.koin.android.ext.android.inject

class TabContainerFragment : Fragment() {
    val viewModel: TabContainerViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  TabContainerFragmentBinding.inflate(inflater).apply {
            viewModel = this@TabContainerFragment.viewModel
            lifecycleOwner = this@TabContainerFragment
        }.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager.adapter = fragmentManager?.let { MainViewPagerAdapter(it, lifecycle) }

        TabLayoutMediator(tabs, viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = "Tab $position"
                Log.d("Tabs", "$position")
            }).attach()
        viewpager.currentItem = 2
    }


}
