package de.klyk.coroutinesadvanced.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import de.klyk.coroutinesadvanced.databinding.MoviePagingFragmentBinding
import kotlinx.android.synthetic.main.movie_paging_fragment.*
import kotlinx.android.synthetic.main.movie_paging_fragment.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MoviePagingFragment : Fragment() {

    val viewModel: MoviePagingFragmentViewModel by inject()
    private val movieAdapter by lazy(LazyThreadSafetyMode.NONE) { MoviePagingAdapter() }
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return MoviePagingFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@MoviePagingFragment
            viewModel = this@MoviePagingFragment.viewModel
        }.root
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)

        subscribeObservers()
    }

    private fun initRecyclerView(view: View) {
        view.movieRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalPagingApi
    private fun subscribeObservers() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.movieFlow.collectLatest { pagingData ->
                Timber.d("submitData to Adapter")
                movieAdapter.submitData(pagingData)
            }
        }

        movieAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading)
                showProgressBar(true)
            else {
                showProgressBar(false)

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(activity, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showProgressBar(display: Boolean) {
        paging_progressBar.visibility = if (display) View.VISIBLE else View.GONE
    }
}