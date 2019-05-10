package com.syntax.android.foodmart.ui

import com.syntax.android.foodmart.model.Cart
import com.syntax.android.foodmart.model.Favorites
import com.syntax.android.foodmart.model.FoodRepository
import com.syntax.android.foodmart.ui.cart.CartContract
import com.syntax.android.foodmart.ui.cart.CartPresenter
import com.syntax.android.foodmart.ui.categories.CategoriesContract
import com.syntax.android.foodmart.ui.categories.CategoriesPresenter
import com.syntax.android.foodmart.ui.categories.CategoryContract
import com.syntax.android.foodmart.ui.categories.CategoryPresenter
import com.syntax.android.foodmart.ui.detail.FoodContract
import com.syntax.android.foodmart.ui.detail.FoodPresenter
import com.syntax.android.foodmart.ui.items.ItemsContract
import com.syntax.android.foodmart.ui.items.ItemsPresenter


object Injection {

  fun provideFoodRepository(): FoodRepository = FoodRepository

  fun provideCart(): Cart = Cart

  fun provideFavorites(): Favorites = Favorites

  fun provideItemsPresenter(itemsView: ItemsContract.View): ItemsContract.Presenter =
      ItemsPresenter(provideFoodRepository(), provideCart(), provideFavorites(), itemsView)

  fun provideFoodPresenter(foodView: FoodContract.View): FoodContract.Presenter =
      FoodPresenter(provideFoodRepository(), provideCart(), foodView)

  fun provideCartPresenter(cartView: CartContract.View): CartContract.Presenter =
      CartPresenter(provideCart(), cartView)

  fun provideCategoriesPresenter(view: CategoriesContract.View): CategoriesContract.Presenter =
      CategoriesPresenter(provideFoodRepository(), view)

  fun provideCategoryPresenter(itemsView: CategoryContract.View): CategoryContract.Presenter =
      CategoryPresenter(provideFoodRepository(), itemsView)
}