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
import de.klyk.coroutinesadvanced.databinding.MoviePagingSeperatorItemBinding
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import de.klyk.coroutinesadvanced.io.db.movies.MovieModel

class MoviePagingAdapter(private val lifecycleOwner: LifecycleOwner) : PagingDataAdapter<MovieModel, RecyclerView.ViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == R.layout.movie_paging_item) MovieViewHolder.create(parent) else SeparatorViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)){
            is MovieModel.MovieItem -> R.layout.movie_paging_item
            is MovieModel.SeperatorItem -> R.layout.movie_paging_seperator_item
            else -> R.layout.movie_paging_seperator_item
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when(it){
                is MovieModel.MovieItem -> {
                    (holder as MovieViewHolder).apply {
                        binding.lifecycleOwner = lifecycleOwner
                        bind(it.movie)
                    }
                }
                is MovieModel.SeperatorItem -> {
                    (holder as SeparatorViewHolder).apply {
                        binding.lifecycleOwner = lifecycleOwner
                        bind(it.descirption)
                    }
                }
            }
        }
    }

    object MovieDiffUtil : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            // Id is unique.
            return (oldItem is MovieModel.MovieItem && newItem is MovieModel.MovieItem && oldItem.movie.imdbID == newItem.movie.imdbID) ||
                    (oldItem is MovieModel.SeperatorItem && newItem is MovieModel.SeperatorItem && oldItem.descirption == newItem.descirption)
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
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

class SeparatorViewHolder(val binding: MoviePagingSeperatorItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(description: String) {
        binding.separatorDescription.text = description
    }

    companion object {
        fun create(parent: ViewGroup): SeparatorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_paging_seperator_item,  parent,false)

            val binding = MoviePagingSeperatorItemBinding.bind(view)

            return SeparatorViewHolder(
                binding
            )
        }
    }
}
