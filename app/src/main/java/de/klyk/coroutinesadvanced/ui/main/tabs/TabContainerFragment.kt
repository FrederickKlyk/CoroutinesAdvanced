package de.klyk.coroutinesadvanced.ui.main.tabs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.databinding.TabContainerFragmentBinding
import de.klyk.coroutinesadvanced.ui.base.viewpager2.FragmentStateViewPagerAdapter
import de.klyk.coroutinesadvanced.ui.main.DummyFragment
import de.klyk.coroutinesadvanced.ui.main.coroutines.*
import kotlinx.android.synthetic.main.tab_container_fragment.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class TabContainerFragment : Fragment() {
    private val viewModel: TabContainerViewModel by inject()
    private lateinit var tabAdapter: FragmentStateViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = TabContainerFragmentBinding.inflate(inflater).apply {
            viewModel = this@TabContainerFragment.viewModel
            lifecycleOwner = this@TabContainerFragment
        }.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabAdapter = FragmentStateViewPagerAdapter(childFragmentManager, lifecycle)
        addFragmentsToViewPager()
        initTabLayout()
    }

    private fun addFragmentsToViewPager() {
        tabAdapter.addFragment(CoroutinesFragment.newInstance(), "Coroutines")
        tabAdapter.addFragment(DummyFragment(), "Channel")
        tabAdapter.addFragment(FlowFragment.newInstance(), "Flow")
        tabAdapter.addFragment(WebsocketsFragment.newInstance(), "Websockets")
        tabAdapter.addFragment(StateSharedFlowFragment.newInstance(), "State-/SharedFlow")
        tabAdapter.addFragment(StateFlowFragment.newInstance(), "StateFlow DataBinding")

        //ViewPager Adapter erhält TabAdapter mit den Fragmenten, die innerhalb des TabAdapter hinzugefügt worden sind
        viewPagerForTabs.adapter = tabAdapter
    }

    private fun initTabLayout() {
        TabLayoutMediator(mainTabs_TL, viewPagerForTabs) { tab, position ->
            //Alle Tab-Title werden gesetzt
            tab.text = tabAdapter.getPageTitle(position)
            Timber.d("Tabs $position")
        }.attach()
        viewPagerForTabs.currentItem = 0
    }
}
