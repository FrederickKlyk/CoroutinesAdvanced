package de.klyk.coroutinesadvanced.ui.paging

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

class MoviePagingAdapter(private val lifecycleOwner: LifecycleOwner) : PagingDataAdapter<Movie, MovieViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner

        getItem(position)?.let {
            holder.bind(it)
        }
    }

    object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}

class MovieViewHolder(val binding: MoviePagingItemBinding) : RecyclerView.ViewHolder(binding.root) {

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
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_paging_item, parent, false)

            val binding = MoviePagingItemBinding.bind(view)

            return MovieViewHolder(binding)
        }
    }
}
