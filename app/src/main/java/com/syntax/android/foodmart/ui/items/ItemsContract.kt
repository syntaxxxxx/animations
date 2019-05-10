package com.syntax.android.foodmart.ui.items

import com.syntax.android.foodmart.model.Food
import com.syntax.android.foodmart.ui.base.BasePresenter
import com.syntax.android.foodmart.ui.base.BaseView


interface ItemsContract {

  interface View : BaseView<Presenter> {
    fun showItems(items: List<Food>)
  }

  interface Presenter : BasePresenter {
    fun cartSize(): Int
    fun addAllToCart()
    fun clearCart()
    fun removeItem(item: Food)
    fun addItem(item: Food)
    fun removeFavorite(item: Food)
    fun addFavorite(item: Food)
  }
}