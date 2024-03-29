package de.klyk.coroutinesadvanced.ui.main.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import de.klyk.coroutinesadvanced.databinding.StateFlowFragmentBinding
import org.koin.android.ext.android.inject

class StateFlowFragment : Fragment() {

    var binding : StateFlowFragmentBinding? = null

    private val viewModel: StateFlowFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StateFlowFragmentBinding.inflate(inflater).apply {
            viewModel = this@StateFlowFragment.viewModel
            lifecycleOwner = this@StateFlowFragment
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonInitListener?.setOnClickListener {
            setFragmentResultListener(COROUTINES_TO_STATE_FLOW_REQUEST_KEY){ _, bundle ->
                val newFriend = bundle.getString(COROUTINES_TO_STATE_FLOW_BUNDLE_FRIEND_KEY)
                newFriend?.let { viewModel.addRepoFriend(it) }
            }
        }
    }

    companion object {
        const val COROUTINES_TO_STATE_FLOW_REQUEST_KEY = "COROUTINES_TO_STATE_FLOW_REQUEST_KEY"
        const val COROUTINES_TO_STATE_FLOW_BUNDLE_FRIEND_KEY = "COROUTINES_TO_STATE_FLOW_BUNDLE_FRIEND"

        fun newInstance() = StateFlowFragment()
    }
}