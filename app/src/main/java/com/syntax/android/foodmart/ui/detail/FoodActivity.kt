package com.syntax.android.foodmart.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.syntax.android.foodmart.R
import com.syntax.android.foodmart.app.toast
import com.syntax.android.foodmart.model.events.CartEvent
import com.syntax.android.foodmart.ui.Injection
import kotlinx.android.synthetic.main.activity_food.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FoodActivity : AppCompatActivity(), FoodContract.View {

  override lateinit var presenter: FoodContract.Presenter

  companion object {
    private const val EXTRA_FOOD_ID = "place_id"

    fun newIntent(context: Context, foodId: Int): Intent {
      val intent = Intent(context, FoodActivity::class.java)
      intent.putExtra(EXTRA_FOOD_ID, foodId)
      return intent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_food)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    presenter = Injection.provideFoodPresenter(this)

    val food = presenter.getFood(intent.extras.getInt(EXTRA_FOOD_ID))

    food?.let {
      foodImage.setImageResource(resources.getIdentifier(food.largeImage, null, packageName))
      collapsingToolbar.title = food.name
      collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
      foodName.text = food.name
      foodDescription.text = food.description
      fab.setImageResource(if (food.isInCart) R.drawable.ic_done else R.drawable.ic_add)
      moreInfo.setOnClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(food.link))
        startActivity(browserIntent)
      }
      fab.setOnClickListener {
        if (food.isInCart) {
          presenter.removeItem(food)
        } else {
          presenter.addItem(food)
        }
      }
      foodImage.setOnClickListener {
        val rotateAndScaleAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_and_scale)
        foodImage.startAnimation(rotateAndScaleAnimation)
        monster.visibility = View.VISIBLE
        rotateAndScaleAnimation.setAnimationListener(object : Animation.AnimationListener {
          override fun onAnimationRepeat(animation: Animation?) {
          }
          override fun onAnimationEnd(animation: Animation?) {
            monster.visibility = View.INVISIBLE
          }
          override fun onAnimationStart(animation: Animation?) {
          }
        })
      }
    }
  }

  override fun onBackPressed() {
    super.onBackPressed()
    fab.visibility = View.GONE
  }

  override fun onResume() {
    super.onResume()
    EventBus.getDefault().register(this)
  }

  override fun onPause() {
    super.onPause()
    EventBus.getDefault().unregister(this)
  }

  @Suppress("UNUSED_PARAMETER")
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCartEvent(event: CartEvent) {
    val food = presenter.getFood(intent.extras.getInt(EXTRA_FOOD_ID))
    val isInCart = food?.isInCart ?: false
    fab.setImageResource(if (isInCart) R.drawable.ic_morph else R.drawable.ic_morph_reverse)
    val animatable = fab.drawable as Animatable
    animatable.start()
    toast(if (isInCart) getString(R.string.added_to_cart) else getString(R.string.removed_from_cart))
  }
}
