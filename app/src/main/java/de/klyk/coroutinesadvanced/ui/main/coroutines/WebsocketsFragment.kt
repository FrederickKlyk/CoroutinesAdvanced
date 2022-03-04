package de.klyk.coroutinesadvanced.ui.main.coroutines


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import de.klyk.coroutinesadvanced.databinding.StateSharedFlowFragmentBinding
import de.klyk.coroutinesadvanced.databinding.WebsocketsFragmentBinding
import org.koin.android.ext.android.inject

class WebsocketsFragment : Fragment() {

    var binding : WebsocketsFragmentBinding? = null

    val viewModel: WebsocketsFragmentViewModel by inject()
    val adapter: WebsocketsFragmentAdapter by lazy { WebsocketsFragmentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WebsocketsFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@WebsocketsFragment
            viewModel = this@WebsocketsFragment.viewModel
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecylerview()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.wsResponse.observe(viewLifecycleOwner, Observer {
            adapter.addMessage(it)
        })
    }

    private fun setupRecylerview() {
        binding?.websocketsMessagesRV?.adapter = adapter
    }

    companion object {
        fun newInstance() = WebsocketsFragment()
    }
}
