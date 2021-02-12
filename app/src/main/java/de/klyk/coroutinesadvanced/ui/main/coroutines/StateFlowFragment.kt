package de.klyk.coroutinesadvanced.ui.main.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.klyk.coroutinesadvanced.databinding.StateFlowFragmentBinding
import org.koin.android.ext.android.inject

class StateFlowFragment : Fragment() {

    private val viewModel: StateFlowFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return StateFlowFragmentBinding.inflate(inflater).apply {
            viewModel = this@StateFlowFragment.viewModel
            lifecycleOwner = this@StateFlowFragment
        }.root
    }

    companion object {
        fun newInstance() = StateFlowFragment()
    }
}