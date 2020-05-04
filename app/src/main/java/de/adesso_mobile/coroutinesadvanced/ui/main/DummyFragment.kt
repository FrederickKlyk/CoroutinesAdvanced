package de.adesso_mobile.coroutinesadvanced.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import de.adesso_mobile.coroutinesadvanced.R
import de.adesso_mobile.coroutinesadvanced.databinding.DummyFragmentBinding
import de.adesso_mobile.coroutinesadvanced.ui.base.BaseFragment
import de.adesso_mobile.coroutinesadvanced.ui.main.tabs.TabContainerFragmentDirections
import kotlinx.android.synthetic.main.dummy_fragment.*

class DummyFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DummyFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@DummyFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  val bundle = bundleOf("test2" to "value1")

        dummy_nav_button.setOnClickListener {
           val action = TabContainerFragmentDirections.actionTabContainerFragmentToDummyFragment2()
            action.message = "meine neue Test Message"
            findNavController().navigate(action)
            // Alternativ -->  findNavController().navigate(R.id.action_tabContainerFragment_to_dummyFragment2, bundle)
        }
    }
}
