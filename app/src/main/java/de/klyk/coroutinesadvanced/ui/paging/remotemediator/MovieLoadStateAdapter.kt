package de.klyk.coroutinesadvanced.ui.paging.remotemediator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.databinding.MoviePagingLoadStateBinding

/** Adapter, der auf die LoadingStates der Paging-Daten im Footer interargiert */
class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadingStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MovieLoadingStateViewHolder {
        return MovieLoadingStateViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: MovieLoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }
}

class MovieLoadingStateViewHolder(private val binding: MoviePagingLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState, retry: () -> Unit) {
        binding.apply {
            loadingStateProgressBar.isVisible = loadState is LoadState.Loading
            loadingStateErrorMessageTV.isVisible = loadState !is LoadState.Loading
            loadingStateRetryButton.isVisible = loadState !is LoadState.Loading

            if (loadState is LoadState.Error) {
                loadingStateErrorMessageTV.text = loadState.error.localizedMessage
            }

            loadingStateRetryButton.setOnClickListener {
                retry.invoke()
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieLoadingStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_paging_load_state, parent, false)

            val binding = MoviePagingLoadStateBinding.bind(view)

            return MovieLoadingStateViewHolder(
                binding
            )
        }
    }
}