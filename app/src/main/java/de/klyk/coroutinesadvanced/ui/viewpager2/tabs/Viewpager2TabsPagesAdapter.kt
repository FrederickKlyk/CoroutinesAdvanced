package de.klyk.coroutinesadvanced.ui.viewpager2.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.domain.viewpager2.Category

class TabViewPagerAdapter : RecyclerView.Adapter<TabViewPagerViewHolder>() {

    private var eventList: List<Category> = listOf()

    fun setItem(list: List<Category>) {
        this.eventList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewPagerViewHolder = TabViewPagerViewHolder(parent)

    override fun onBindViewHolder(holder: TabViewPagerViewHolder, position: Int) {
        holder.bind(eventList[position].name)
    }

    override fun getItemCount(): Int = eventList.size
}

class TabViewPagerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) : this(LayoutInflater.from(parent.context).inflate(R.layout.vierpager2_tab_cell, parent, false))

    fun bind(text: String) {
        val textTV = itemView.findViewById<TextView>(R.id.text_TV)
        textTV.text = text
    }
}