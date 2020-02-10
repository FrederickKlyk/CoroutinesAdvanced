package de.adesso_mobile.coroutinesadvanced.ui.main.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.adesso_mobile.coroutinesadvanced.databinding.FlowFragmentBinding
import org.koin.android.ext.android.inject

class FlowFragment : Fragment() {
    private val viewModel: FlowFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FlowFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@FlowFragment
            viewModel = this@FlowFragment.viewModel
        }.root
    }

    companion object {
        fun newInstance() = FlowFragment()
    }
}
