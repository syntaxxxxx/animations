
package com.syntax.android.foodmart.model

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.syntax.android.foodmart.app.FoodMartApplication
import com.syntax.android.foodmart.model.events.CartDeleteItemEvent
import com.syntax.android.foodmart.model.events.CartEvent
import org.greenrobot.eventbus.EventBus


object Cart {

  private val KEY_CART = "KEY_CART"
  private val gson = Gson()

  private var itemIds: MutableList<Int>? = null

  fun isInCart(item: Food): Boolean {
    return getCartItemIds()?.contains(item.id) == true
  }

  fun addItem(item: Food) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isInCart = true
      itemIds.add(item.id)
      saveCart(KEY_CART, itemIds)
    }
  }

  fun removeItem(item: Food) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isInCart = false
      val position = itemIds.indexOf(item.id)
      itemIds.remove(item.id)
      saveCart(KEY_CART, itemIds)
      EventBus.getDefault().post(CartDeleteItemEvent(position))
    }
  }

  private fun saveCart(key: String, list: List<Int>) {
    val json = gson.toJson(list)
    sharedPrefs().edit().putString(key, json).apply()
    EventBus.getDefault().post(CartEvent())
  }

  private fun getCartItemIds(): MutableList<Int>? {
    if (itemIds == null) {
      val json = sharedPrefs().getString(KEY_CART, "")
      val type = object : TypeToken<MutableList<Int>>() {}.type
      itemIds = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
    }
    return itemIds
  }

  fun getCartItems(): List<Food> {
    val itemIds = getCartItemIds()
    val items = mutableListOf<Food>()
    itemIds?.let {
      itemIds.forEach {
        val item = FoodRepository.getFoodById(it)
        item?.let {
          items.add(item)
        }
      }
    }
    return items
  }

  fun cartSize(): Int {
    val itemIds = getCartItemIds()
    itemIds?.let {
      return itemIds.size
    }
    return 0
  }

  fun addAllToCart() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      val foods = FoodRepository.getFoods()
      foods.forEach { food ->
        if (!food.isInCart) {
          addItem(food)
        }
      }
    }
  }

  fun clearCart() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      itemIds.clear()
      val foods = FoodRepository.getFoods()
      foods.forEach { it.isInCart = false }
      saveCart(KEY_CART, itemIds)
    }
  }

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(FoodMartApplication.getAppContext())
}