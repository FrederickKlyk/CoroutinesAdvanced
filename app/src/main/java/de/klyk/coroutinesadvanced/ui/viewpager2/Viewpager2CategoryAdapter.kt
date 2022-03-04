package de.klyk.coroutinesadvanced.ui.viewpager2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.klyk.coroutinesadvanced.R
import de.klyk.coroutinesadvanced.domain.viewpager2.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {
    private var list: List<Category> = listOf()

    fun setItem(list: List<Category>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(parent)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class CategoryViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) : this(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))

    fun bind(category: Category) {
        val categoryName = itemView.findViewById<TextView>(R.id.categoryName)
        categoryName.text = category.name
    }
}