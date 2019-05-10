
package com.syntax.android.foodmart.model


data class Food(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val link: String,
    val category: String,
    var isInCart: Boolean = false,
    var isFavorite: Boolean = false) {

  val thumbnail: String
    get() = "drawable/$image"

  val largeImage: String
    get() = "drawable/$image"
}