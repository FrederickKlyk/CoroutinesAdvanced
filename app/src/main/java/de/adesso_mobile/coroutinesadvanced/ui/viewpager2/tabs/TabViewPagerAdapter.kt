package de.adesso_mobile.coroutinesadvanced.ui.viewpager2.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.adesso_mobile.coroutinesadvanced.R
import de.adesso_mobile.coroutinesadvanced.domain.viewpager2.Category
import kotlinx.android.synthetic.main.vierpager2_tab_cell.view.*

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
        itemView.text_TV.text = text
    }
}