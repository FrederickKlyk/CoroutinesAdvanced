package de.klyk.coroutinesadvanced.ui.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.io.db.movies.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movie_paging_item.view.*

class MoviePagingAdapter() : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_paging_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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

    inner class MovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Movie?) {
            containerView.apply {
                movieItemImageView.load(item?.poster) {
                    crossfade(true)
                    crossfade(300)
                }
                movieItemTitleTv.text = item?.title
                movieItemYearTv.text = item?.year
                movieItemTypeTv.text = item?.type
                movieItemImdbIDTv.text = item?.imdbID
            }
        }
    }
}
