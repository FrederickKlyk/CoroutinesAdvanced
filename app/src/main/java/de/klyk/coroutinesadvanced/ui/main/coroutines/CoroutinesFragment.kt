package de.klyk.coroutinesadvanced.ui.main.coroutines


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import de.klyk.coroutinesadvanced.databinding.CoroutinesFragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import de.klyk.coroutinesadvanced.ui.main.coroutines.StateFlowFragment.Companion.COROUTINES_TO_STATE_FLOW_BUNDLE_FRIEND_KEY
import de.klyk.coroutinesadvanced.ui.main.coroutines.StateFlowFragment.Companion.COROUTINES_TO_STATE_FLOW_REQUEST_KEY
import de.klyk.coroutinesadvanced.utils.NotificationUtil
import kotlinx.android.synthetic.main.coroutines_fragment.*
import kotlinx.android.synthetic.main.coroutines_fragment.view.*
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject

class CoroutinesFragment : BaseFragment() {

    private val viewModel: CoroutinesFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CoroutinesFragmentBinding.inflate(inflater).apply {
            viewModel = this@CoroutinesFragment.viewModel
            lifecycleOwner = this@CoroutinesFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.coroutinesToStateFlowButton.setOnClickListener {
            val friendName = view.coroutinesToStateFlowET.text.toString()
            setFragmentResult(
                COROUTINES_TO_STATE_FLOW_REQUEST_KEY,
                bundleOf(COROUTINES_TO_STATE_FLOW_BUNDLE_FRIEND_KEY to friendName)
            )
            showSnackbar("StateFlowBindingFragment wurde der Freund \"$friendName\" über setFragmentResult übergeben..")
        }
    }

    private fun showSnackbar(description: String) {
        coroutinesCoordinatorLayout.let {
            NotificationUtil.createSnackBar(it, description).show()
        }
    }

    companion object {
        fun newInstance() = CoroutinesFragment()
    }
}
