package de.klyk.coroutinesadvanced.ui.paging.pagingsource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.databinding.MoviePagingItemBinding
import de.klyk.coroutinesadvanced.io.db.movies.Movie

class MoviePagingSourceAdapter(
    private val lifecycleOwner: LifecycleOwner
) : PagingDataAdapter<Movie, MoviePagingSourceViewHolder>(MoviePagingSoruceDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagingSourceViewHolder {
        return MoviePagingSourceViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MoviePagingSourceViewHolder, position: Int) {
        getItem(position)?.let {
            holder.apply {
                binding.lifecycleOwner = lifecycleOwner
                bind(it)
            }
        }
    }

    object MoviePagingSoruceDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}

class MoviePagingSourceViewHolder(val binding: MoviePagingItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie) {
        binding.apply {
            movieItemImageView.load(item.poster) {
                crossfade(true)
            }
            movieItemTitleTv.text = item.title
            movieItemYearTv.text = item.year
            movieItemTypeTv.text = item.type
            movieItemImdbIDTv.text = item.imdbID
        }
    }

    companion object {
        fun create(parent: ViewGroup): MoviePagingSourceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_paging_item, parent, false)

            val binding = MoviePagingItemBinding.bind(view)

            return MoviePagingSourceViewHolder(
                binding
            )
        }
    }
}
