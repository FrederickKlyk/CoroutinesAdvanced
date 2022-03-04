package de.klyk.coroutinesadvanced.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.klyk.coroutinesadvanced.R

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

    inner class ListFragmentViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        fun bind(text: String) {
            val itemTV = containerView.findViewById<TextView>(R.id.item_tv)
            itemTV.text = text
        }
    }
}