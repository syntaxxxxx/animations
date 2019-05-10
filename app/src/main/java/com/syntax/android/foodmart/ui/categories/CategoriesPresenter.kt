package com.syntax.android.foodmart.ui.categories

import com.syntax.android.foodmart.model.FoodRepository


class CategoriesPresenter(private val repository: FoodRepository, private val view: CategoriesContract.View)
  : CategoriesContract.Presenter {

  override fun start() {
    loadCategories()
  }

  private fun loadCategories() {
    view.showCategories(repository.getCategories())
  }
}