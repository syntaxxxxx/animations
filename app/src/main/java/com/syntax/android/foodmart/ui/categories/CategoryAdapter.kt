package com.syntax.android.foodmart.ui.categories

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.syntax.android.foodmart.R
import com.syntax.android.foodmart.app.inflate
import com.syntax.android.foodmart.model.Food
import kotlinx.android.synthetic.main.list_item_food.view.*


class CategoryAdapter(private val items: MutableList<Food>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(parent.inflate(R.layout.list_item_category_food))

  override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount() = items.size

  fun updateItems(items: List<Food>) {
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Food) {
      val context = itemView.context
      itemView.foodImage.setImageResource(context.resources.getIdentifier(item.thumbnail, null, context.packageName))
      itemView.name.text = item.name
    }
  }
}