package de.klyk.coroutinesadvanced.ui.main.coroutines


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.databinding.WebsocketsFragmentBinding
import de.klyk.coroutinesadvanced.ui.base.BaseFragment
import kotlinx.android.synthetic.main.websockets_fragment.*
import org.koin.android.ext.android.inject

class WebsocketsFragment : Fragment() {

    val viewModel: WebsocketsFragmentViewModel by inject()
    val adapter: WebsocketsFragmentAdapter by lazy { WebsocketsFragmentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return WebsocketsFragmentBinding.inflate(inflater).apply {
           lifecycleOwner = this@WebsocketsFragment
           viewModel = this@WebsocketsFragment.viewModel
       }.root
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
        websocketsMessagesRV.adapter = adapter
    }

    companion object {
        fun newInstance() = WebsocketsFragment()
    }
}
