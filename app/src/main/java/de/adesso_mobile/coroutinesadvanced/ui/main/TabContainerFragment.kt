package de.adesso_mobile.coroutinesadvanced.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import de.adesso_mobile.coroutinesadvanced.databinding.TabContainerFragmentBinding
import de.adesso_mobile.coroutinesadvanced.ui.base.viewpager2.MainViewPagerAdapter
import de.adesso_mobile.coroutinesadvanced.ui.coroutines.CoroutinesFragment
import kotlinx.android.synthetic.main.tab_container_fragment.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class TabContainerFragment : Fragment() {
    private val viewModel: TabContainerViewModel by inject()
    private lateinit var tabAdapter: MainViewPagerAdapter

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

        tabAdapter = MainViewPagerAdapter(childFragmentManager, lifecycle)
        addFragmentsToViewPager()
        initTabLayout()
    }

    private fun addFragmentsToViewPager() {
        tabAdapter.addFragment(CoroutinesFragment.newInstance(), "Coroutines")
        tabAdapter.addFragment(DummyFragment(), "Channel")
        tabAdapter.addFragment(DummyFragment(), "Flow")

        //ViewPager Adapter erhält TabAdapter mit den Fragmenten, die innerhalb des TabAdapter hinzugefügt worden sind
        mainViewPager.adapter = tabAdapter
    }

    private fun initTabLayout() {
        TabLayoutMediator(mainTabs_TL, mainViewPager){ tab, position ->
                tab.text = tabAdapter.getPageTitle(position)
                Timber.d("Tabs $position")
            }.attach()
        mainViewPager.currentItem = 0
    }
}
