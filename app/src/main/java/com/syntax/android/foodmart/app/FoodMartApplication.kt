
package com.syntax.android.foodmart.app

import android.app.Application
import android.content.Context
import com.syntax.android.foodmart.model.FoodRepository


class FoodMartApplication : Application() {

  companion object {
    private lateinit var instance: FoodMartApplication

    fun getAppContext(): Context = instance.applicationContext
  }

  override fun onCreate() {
    instance = this
    super.onCreate()

    // Initialize FoodRepository
    FoodRepository.loadFoods(this)
  }
}