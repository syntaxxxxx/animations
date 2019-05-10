package com.syntax.android.foodmart.ui.detail

import com.syntax.android.foodmart.model.Food
import com.syntax.android.foodmart.ui.base.BasePresenter
import com.syntax.android.foodmart.ui.base.BaseView


interface FoodContract {
  interface View : BaseView<Presenter>

  interface Presenter : BasePresenter {
    fun getFood(foodId: Int): Food?
    fun removeItem(item: Food)
    fun addItem(item: Food)
  }
}