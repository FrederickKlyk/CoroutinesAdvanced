package de.adesso_mobile.coroutinesadvanced.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import de.adesso_mobile.coroutinesadvanced.databinding.MoviePagingFragmentBinding
import kotlinx.android.synthetic.main.movie_paging_fragment.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MoviePagingFragment : Fragment() {

    val viewModel: MoviePagingFragmentViewModel by inject()
    private val movieAdapter by lazy(LazyThreadSafetyMode.NONE) { MoviePagingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return MoviePagingFragmentBinding.inflate(inflater).apply {
            lifecycleOwner = this@MoviePagingFragment
            viewModel = this@MoviePagingFragment.viewModel
        }.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.movieRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        subscribeObservers()
    }

    @ExperimentalPagingApi
    private fun subscribeObservers() {
        lifecycleScope.launch {
            viewModel.movies.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }
}