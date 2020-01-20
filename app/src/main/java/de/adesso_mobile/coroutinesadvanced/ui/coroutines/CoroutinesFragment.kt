package de.adesso_mobile.coroutinesadvanced.ui.coroutines


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.adesso_mobile.coroutinesadvanced.databinding.CoroutinesFragmentBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

class CoroutinesFragment : Fragment() {

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

    companion object{

        fun newInstance() = CoroutinesFragment()
    }
}
