package de.adesso_mobile.coroutinesadvanced.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.adesso_mobile.coroutinesadvanced.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_fragment_item.view.*

class ListFragmentAdapter : ListAdapter<String, ListFragmentAdapter.ListFragmentViewHolder>(ListFragmentAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFragmentViewHolder =
        ListFragmentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_fragment_item, parent, false))

    override fun onBindViewHolder(holder: ListFragmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ListFragmentAdapterListDiff : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    inner class ListFragmentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(text: String) {
            itemView.item_tv.text = text
        }
    }
}