package de.klyk.coroutinesadvanced.ui.main.coroutines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.klyk.coroutinesadvanced.databinding.WebsocketsListItemBinding

class WebsocketsFragmentAdapter : RecyclerView.Adapter<WebsocketsFragmentAdapter.WebsocketsViewholder>() {

    private val chatMessagesList = mutableListOf<String>()

    fun addMessage(message: String) {
        chatMessagesList.add(message)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsocketsViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WebsocketsListItemBinding.inflate(inflater)

        return WebsocketsViewholder(binding)
    }

    override fun getItemCount(): Int = chatMessagesList.size

    override fun onBindViewHolder(holder: WebsocketsViewholder, position: Int) {
        holder.bind(chatMessagesList[position])
    }

    inner class WebsocketsViewholder(val binding: WebsocketsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: String) {
            binding.viewModel = WebsocketsFragmentItemViewModel(message)
            binding.executePendingBindings()
        }
    }
}