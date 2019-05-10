package com.syntax.android.foodmart.ui.cart

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.syntax.android.foodmart.R
import com.syntax.android.foodmart.app.inflate
import com.syntax.android.foodmart.model.Food
import kotlinx.android.synthetic.main.list_item_cart.view.*


class CartAdapter(private val items: MutableList<Food>, private val listener: CartAdapterListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      ViewHolder(parent.inflate(R.layout.list_item_cart))

  override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) =
    holder.bind(items[position])

  override fun getItemCount() = items.size

  fun updateItems(items: List<Food>, notify: Boolean) {
    this.items.clear()
    this.items.addAll(items)
    if (notify) notifyDataSetChanged()
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: Food

    fun bind(item: Food) {
      this.item = item
      val context = itemView.context
      itemView.foodImage.setImageResource(context.resources.getIdentifier(item.thumbnail, null, context.packageName))
      itemView.foodName.text = item.name
      itemView.deleteButton.setOnClickListener {
        if (item.isInCart) {
          listener.removeItem(item)
        }
      }
    }
  }

  interface CartAdapterListener {
    fun removeItem(item: Food)
  }
}