package com.syntax.android.foodmart.ui.categories

import com.syntax.android.foodmart.ui.base.BasePresenter
import com.syntax.android.foodmart.ui.base.BaseView


interface CategoriesContract {

  interface View : BaseView<CategoriesContract.Presenter> {
    fun showCategories(categories: List<String>)
  }

  interface Presenter : BasePresenter
}