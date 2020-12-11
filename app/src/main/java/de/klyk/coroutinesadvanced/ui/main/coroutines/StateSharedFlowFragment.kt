package de.klyk.coroutinesadvanced.ui.main.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import de.klyk.coroutinesadvanced.databinding.StateSharedFlowFragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import kotlinx.android.synthetic.main.state_shared_flow_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class StateSharedFlowFragment : BaseFragment() {

    private val viewModel: StateSharedFlowFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return StateSharedFlowFragmentBinding.inflate(inflater).apply {
            viewModel = this@StateSharedFlowFragment.viewModel
            lifecycleOwner = this@StateSharedFlowFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
    }

    private fun observeViewModelEvents() {
        lifecycleScope.launch {
            viewModel.nameStateFlow.collect { _ ->
                viewModel.incrementStateFlowCounter()
            }
        }
        lifecycleScope.launch {
            viewModel.myStateFlowCounter.collect { value ->
                stateFlowCounterTV.text = "Anzahl der Emittierungen: $value"
            }
        }
    }

    companion object {
        fun newInstance() = StateSharedFlowFragment()
    }
}