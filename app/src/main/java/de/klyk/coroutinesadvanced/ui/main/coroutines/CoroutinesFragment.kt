package de.klyk.coroutinesadvanced.ui.main.coroutines


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.klyk.coroutinesadvanced.databinding.CoroutinesFragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import org.koin.android.ext.android.inject

class CoroutinesFragment : BaseFragment() {

    private val viewModel: CoroutinesFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return CoroutinesFragmentBinding.inflate(inflater).apply {
            viewModel = this@CoroutinesFragment.viewModel
            lifecycleOwner = this@CoroutinesFragment
        }.root
    }

    companion object {
        fun newInstance() = CoroutinesFragment()
    }
}
