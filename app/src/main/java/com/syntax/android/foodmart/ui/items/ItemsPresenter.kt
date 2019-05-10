package com.syntax.android.foodmart.ui.items

import com.syntax.android.foodmart.model.Cart
import com.syntax.android.foodmart.model.Favorites
import com.syntax.android.foodmart.model.Food
import com.syntax.android.foodmart.model.FoodRepository


class ItemsPresenter(private val repository: FoodRepository, private val cart: Cart, private val favorites: Favorites, private val itemsView: ItemsContract.View)
  : ItemsContract.Presenter {

  override fun start() {
    loadFoods()
  }

  private fun loadFoods() {
    itemsView.showItems(repository.getFoods())
  }

  override fun cartSize() = cart.cartSize()

  override fun addAllToCart() {
    cart.addAllToCart()
  }

  override fun clearCart() {
    cart.clearCart()
  }

  override fun removeItem(item: Food) {
    cart.removeItem(item)
  }

  override fun addItem(item: Food) {
    cart.addItem(item)
  }

  override fun removeFavorite(item: Food) {
    favorites.removeFavorite(item)
  }

  override fun addFavorite(item: Food) {
    favorites.addFavorite(item)
  }
}