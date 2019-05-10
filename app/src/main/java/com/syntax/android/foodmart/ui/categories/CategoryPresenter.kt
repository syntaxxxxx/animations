package com.syntax.android.foodmart.ui.categories

import com.syntax.android.foodmart.model.FoodRepository


class CategoryPresenter(private val repository: FoodRepository, private val itemsView: CategoryContract.View)
  : CategoryContract.Presenter {

  override fun start() {
  }

  override fun loadCategory(category: String) {
    itemsView.showItems(repository.getFoodsForCategory(category))
  }
}