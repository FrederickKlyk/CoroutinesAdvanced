package de.klyk.coroutinesadvanced.ui.paging.remotemediator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.klyk.coroutinesadvanced.databinding.MoviePagingFragmentBinding
import de.klyk.coroutinesadvanced.databinding.MoviePagingPagingSourceFragmentBinding
import de.klyk.coroutinesadvanced.ui.paging.MovieLoadStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MoviePagingRemoteFragment : Fragment() {
    var binding : MoviePagingFragmentBinding? = null

    val viewModelRemote: MoviePagingRemoteFragmentViewModel by inject()
    private val movieAdapter by lazy(LazyThreadSafetyMode.NONE) { MoviePagingAdapter(this) }
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MoviePagingFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@MoviePagingRemoteFragment
            viewModel = this@MoviePagingRemoteFragment.viewModelRemote
        }
        binding?.root?.let { initRecyclerView(it) }

        return binding?.root
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
    }

    private fun initRecyclerView(view: View) {
        binding?.movieRecyclerRemote?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter.withLoadStateFooter(footer = MovieLoadStateAdapter(movieAdapter::retry))
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalPagingApi
    private fun subscribeObservers() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModelRemote.movieFlow.collectLatest { pagingData ->
                Timber.d("submitData to Adapter")
                movieAdapter.submitData(pagingData)
            }
        }

        // LoadingStates zum allgemeinen verarbeiten in der UI
        movieAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading)
                showProgressBar(true)
            else {
                showProgressBar(false)

                // Fehler anzeigen per Toast
                val errorState = loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                    ?: loadState.refresh as? LoadState.Error

                errorState?.let {
                    Toast.makeText(activity, it.error.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showProgressBar(display: Boolean) {
        binding?.pagingProgressBar?.visibility = if (display) View.VISIBLE else View.GONE
    }
}