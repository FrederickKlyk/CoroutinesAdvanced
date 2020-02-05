package de.adesso_mobile.coroutinesadvanced.ui.viewpager2.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.adesso_mobile.coroutinesadvanced.R
import de.adesso_mobile.coroutinesadvanced.domain.viewpager2.Category
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {
    private var list: List<Category> = listOf()

    fun setItem(list: List<Category>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder = CategoryViewHolder(parent)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}


class CategoryViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) : this(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))

    fun bind(category: Category) {
        itemView.categoryName.text = category.name
    }
}