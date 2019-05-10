package com.syntax.android.foodmart.ui.categories

import com.syntax.android.foodmart.model.Food
import com.syntax.android.foodmart.ui.base.BasePresenter
import com.syntax.android.foodmart.ui.base.BaseView


interface CategoryContract {

  interface View : BaseView<CategoryContract.Presenter> {
    fun showItems(items: List<Food>)
  }

  interface Presenter : BasePresenter {
    fun loadCategory(category: String)
  }
}