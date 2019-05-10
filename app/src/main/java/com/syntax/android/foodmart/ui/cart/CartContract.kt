package com.syntax.android.foodmart.ui.cart

import com.syntax.android.foodmart.model.Food
import com.syntax.android.foodmart.ui.base.BasePresenter
import com.syntax.android.foodmart.ui.base.BaseView


interface CartContract {

  interface View : BaseView<Presenter> {
    fun showCart(items: List<Food>, notify: Boolean)
  }

  interface Presenter : BasePresenter {
    fun loadCart(notify: Boolean)
    fun removeItem(item: Food)
  }
}