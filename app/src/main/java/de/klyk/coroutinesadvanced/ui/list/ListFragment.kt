package de.klyk.coroutinesadvanced.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.klyk.coroutinesadvanced.databinding.ListFragmentBinding
import org.koin.android.ext.android.inject
import kotlin.LazyThreadSafetyMode.NONE


class ListFragment : Fragment() {

    val viewModel: ListFragmentViewModel by inject()
    private val adapter by lazy(NONE) { ListFragmentAdapter() }
    val binding: ListFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ListFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@ListFragment
            viewModel = this@ListFragment.viewModel
        }

        binding.rv1.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()

        binding?.listFragmentButton?.setOnClickListener {
            adapter.submitList(listOf("1", "2", "3", "4", "5"))
        }
    }

    private fun subscribeObservers() {
        adapter.submitList(listOf("8", "3", "9", "1"))
    }
}