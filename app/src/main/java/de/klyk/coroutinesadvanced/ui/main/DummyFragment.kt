package de.klyk.coroutinesadvanced.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import de.klyk.coroutinesadvanced.databinding.DummyFragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import de.klyk.coroutinesadvanced.ui.main.tabs.TabContainerFragmentDirections
import de.klyk.coroutinesadvanced.ui.overviewlibs.OverviewLibsFragmentDirections
import kotlinx.android.synthetic.main.dummy_fragment.*
import org.koin.android.ext.android.inject

class DummyFragment : BaseFragment() {
    var binding: DummyFragmentBinding? = null
    val sharedViewModel: MainSharedViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DummyFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@DummyFragment
        }

        sharedViewModel.onBottomBarVisibleEvent.value = true

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  val bundle = bundleOf("test2" to "value1")

        binding?.dummyNavButton?.setOnClickListener {
            val action = TabContainerFragmentDirections.actionTabContainerFragmentToDummyFragment2()
            action.message = "meine neue Test Message"
            findNavController().navigate(action)
            // Alternativ -->  findNavController().navigate(R.id.action_tabContainerFragment_to_dummyFragment2, bundle)
        }

        dummy_nav_module_button.setOnClickListener {
            val action = TabContainerFragmentDirections.actionMenuCoroutinesToDummyModule()
            findNavController().navigate(action)
        }
    }
}
