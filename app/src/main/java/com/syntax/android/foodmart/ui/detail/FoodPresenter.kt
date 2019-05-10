package com.syntax.android.foodmart.ui.detail

import com.syntax.android.foodmart.model.Cart
import com.syntax.android.foodmart.model.Food
import com.syntax.android.foodmart.model.FoodRepository


class FoodPresenter(private val repository: FoodRepository, private val cart: Cart, private val foodView: FoodContract.View) : FoodContract.Presenter {

  private var food: Food? = null

  override fun start() {
  }

  override fun getFood(foodId: Int): Food? {
    food?.let {
      return it
    }
    food = repository.getFoodById(foodId)
    return food
  }

  override fun removeItem(item: Food) {
    cart.removeItem(item)
  }

  override fun addItem(item: Food) {
    cart.addItem(item)
  }
}