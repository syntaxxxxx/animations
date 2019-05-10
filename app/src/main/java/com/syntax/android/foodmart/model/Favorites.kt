
package com.syntax.android.foodmart.model

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.syntax.android.foodmart.app.FoodMartApplication


object Favorites {

  private val KEY_FAVORITES = "KEY_FAVORITES"
  private val gson = Gson()

  private var favorites: MutableList<Int>? = null

  fun isFavorite(food: Food): Boolean = getFavorites()?.contains(food.id) == true

  fun addFavorite(food: Food) {
    val favorites = getFavorites()
    favorites?.let {
      food.isFavorite = true
      favorites.add(food.id)
      saveFavorites(KEY_FAVORITES, favorites)
    }
  }

  fun removeFavorite(food: Food) {
    val favorites = getFavorites()
    favorites?.let {
      food.isFavorite = false
      favorites.remove(food.id)
      saveFavorites(KEY_FAVORITES, favorites)
    }
  }

  fun getFavorites(): MutableList<Int>? {
    if (favorites == null) {
      val json = sharedPrefs().getString(KEY_FAVORITES, "")
      val type = object : TypeToken<MutableList<Int>>() {}.type
      favorites = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
    }
    return favorites
  }

  fun saveFavorites(list: List<Int>) {
    saveFavorites(KEY_FAVORITES, list)
  }

  private fun saveFavorites(key: String, list: List<Int>) {
    val json = gson.toJson(list)
    sharedPrefs().edit().putString(key, json).apply()
  }

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(FoodMartApplication.getAppContext())
}