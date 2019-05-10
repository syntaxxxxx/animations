package com.syntax.android.foodmart.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.syntax.android.foodmart.ui.items.ItemsActivity

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val intent = Intent(this, ItemsActivity::class.java)
    startActivity(intent)
    finish()
  }
}
