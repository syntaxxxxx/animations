package com.syntax.android.foodmart.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


object FoodRepository {

  private val TAG = "FoodRepository"

  private lateinit var foods: List<Food>

  fun loadFoods(context: Context) {
    val gson = Gson()
    val json = loadJSONFromAsset("foods.json", context)
    val listType = object : TypeToken<List<Food>>() {}.type
    foods = gson.fromJson(json, listType)
    foods
        .filter { Cart.isInCart(it) }
        .forEach { it.isInCart = true }
    foods
        .filter { Favorites.isFavorite(it) }
        .forEach { it.isFavorite = true }
  }

  fun getFoods() = foods.sortedBy { it.name }

  fun getFoodsForCategory(category: String) = foods.filter { it.category == category }.sortedBy { it.name }

  fun getFoodById(id: Int) = foods.firstOrNull { it.id == id }

  fun getCategories() = foods.map { it.category}.distinctBy { it }.sortedBy { it }

  private fun loadJSONFromAsset(filename: String, context: Context): String? {
    var json: String? = null
    try {
      val inputStream = context.assets.open(filename)
      val size = inputStream.available()
      val buffer = ByteArray(size)
      inputStream.read(buffer)
      inputStream.close()
      json = String(buffer)
    } catch (ex: IOException) {
      Log.e(TAG, "Error reading from $filename", ex)
    }
    return json
  }
}