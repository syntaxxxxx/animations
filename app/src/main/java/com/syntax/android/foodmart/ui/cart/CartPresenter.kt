package com.syntax.android.foodmart.ui.cart

import com.syntax.android.foodmart.model.Cart
import com.syntax.android.foodmart.model.Food


class CartPresenter(private val cart: Cart, private val cartView: CartContract.View) : CartContract.Presenter {
  override fun start() {
    loadCart(true)
  }

  override fun loadCart(notify: Boolean) {
    cartView.showCart(cart.getCartItems(), notify)
  }

  override fun removeItem(item: Food) {
    cart.removeItem(item)
  }
}