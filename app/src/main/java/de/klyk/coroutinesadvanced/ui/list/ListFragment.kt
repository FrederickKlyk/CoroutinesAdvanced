package de.klyk.coroutinesadvanced.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.klyk.coroutinesadvanced.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.list_fragment.view.*
import org.koin.android.ext.android.inject
import kotlin.LazyThreadSafetyMode.NONE


class ListFragment : Fragment() {

    val viewModel: ListFragmentViewModel by inject()
    private val adapter by lazy(NONE) { ListFragmentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=  ListFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@ListFragment
            viewModel = this@ListFragment.viewModel
        }.root

        view.rv1.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()

        view.listFragmentButton.setOnClickListener{
            adapter.submitList(listOf("1", "2", "3", "4", "5"))
        }
    }

    private fun subscribeObservers() {
        adapter.submitList(listOf("8", "3", "9", "1"))
    }
}