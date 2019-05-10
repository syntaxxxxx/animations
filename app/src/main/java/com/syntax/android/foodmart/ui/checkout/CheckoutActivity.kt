package com.syntax.android.foodmart.ui.checkout

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import com.syntax.android.foodmart.R
import com.syntax.android.foodmart.app.toast
import kotlinx.android.synthetic.main.activity_checkout.*


class CheckoutActivity : AppCompatActivity() {

  private var donutFlingCount = 0
  private var cookieFlingCount = 0

  private val donutGestureListener = object : GestureDetector.SimpleOnGestureListener() {
    override fun onDown(e: MotionEvent?) = true
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
      if (donutFlingCount < 1) {
        donutFlingAnimationX.setStartVelocity(velocityX)
        donutFlingAnimationY.setStartVelocity(velocityY)

        donutFlingAnimationX.start()
        donutFlingAnimationY.start()
      }
      return true
    }
  }

  private val cookieGestureListener = object : GestureDetector.SimpleOnGestureListener() {
    override fun onDown(e: MotionEvent?) = true
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
      if (cookieFlingCount < 1) {
        cookieFlingAnimationX.setStartVelocity(velocityX)
        cookieFlingAnimationY.setStartVelocity(velocityY)

        cookieFlingAnimationX.start()
        cookieFlingAnimationY.start()
      }
      return true
    }
  }

  private val donutGestureDetector: GestureDetector by lazy {
    GestureDetector(this, donutGestureListener)
  }

  private val cookieGestureDetector: GestureDetector by lazy {
    GestureDetector(this, cookieGestureListener)
  }

  private val donutFlingAnimationX: FlingAnimation by lazy {
    FlingAnimation(donut, DynamicAnimation.X).setFriction(1f)
  }

  private val donutFlingAnimationY: FlingAnimation by lazy {
    FlingAnimation(donut, DynamicAnimation.Y).setFriction(1f)
  }

  private val cookieFlingAnimationX: FlingAnimation by lazy {
    FlingAnimation(cookie, DynamicAnimation.X).setFriction(1f)
  }

  private val cookieFlingAnimationY: FlingAnimation by lazy {
    FlingAnimation(cookie, DynamicAnimation.Y).setFriction(1f)
  }

  companion object {
    fun newIntent(context: Context) = Intent(context, CheckoutActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_checkout)

    title = getString(R.string.checkout_title)

    setupAnimatingBlock()
    setupTouchListener()
    setupTreeObserver()
    setupEndListener()
  }

  private fun setupAnimatingBlock() {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels

    ObjectAnimator.ofFloat(block, "translationX", 0f, width.toFloat() - resources.getDimension(R.dimen.block_width)).apply {
      interpolator = AccelerateDecelerateInterpolator()
      duration = 1000L
      repeatCount = ObjectAnimator.INFINITE
      repeatMode = ObjectAnimator.REVERSE
      start()
    }
  }

  private fun setupTouchListener() {
    donut.setOnTouchListener { _, motionEvent ->
      donutGestureDetector.onTouchEvent(motionEvent)
    }
    cookie.setOnTouchListener { _, motionEvent ->
      cookieGestureDetector.onTouchEvent(motionEvent)
    }
  }

  private fun setupTreeObserver() {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    val height = displayMetrics.heightPixels
    donut.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        donutFlingAnimationX.setMinValue(0f).setMaxValue((width - donut.width).toFloat())
        donutFlingAnimationY.setMinValue(0f).setMaxValue((height - 2 * donut.height).toFloat())
        donut.viewTreeObserver.removeOnGlobalLayoutListener(this)
      }
    })
    cookie.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        cookieFlingAnimationX.setMinValue(0f).setMaxValue((width - cookie.width).toFloat())
        cookieFlingAnimationY.setMinValue(0f).setMaxValue((height - 2 * cookie.height).toFloat())
        cookie.viewTreeObserver.removeOnGlobalLayoutListener(this)
      }
    })
  }

  private fun setupEndListener() {
    donutFlingAnimationX.addEndListener { _, _, _, _ ->
      donutFlingCount += 1
      if (isViewOverlapping(donut, block)) {
        toast(getString(R.string.free_donuts))
      }
    }
    cookieFlingAnimationX.addEndListener { _, _, _, _ ->
      cookieFlingCount += 1
      if (isViewOverlapping(cookie, block)) {
        toast(getString(R.string.free_cookies))
      }
    }
  }

  private fun isViewOverlapping(v1: View, v2: View): Boolean {
    val rect1 = Rect()
    v1.getHitRect(rect1)

    val rect2 = Rect()
    v2.getHitRect(rect2)

    return Rect.intersects(rect1, rect2)
  }
}
