package com.syntax.android.foodmart.ui.cart

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewAnimationUtils
import com.syntax.android.foodmart.R
import com.syntax.android.foodmart.model.Food
import com.syntax.android.foodmart.model.events.CartDeleteItemEvent
import com.syntax.android.foodmart.ui.Injection
import com.syntax.android.foodmart.ui.checkout.CheckoutActivity
import kotlinx.android.synthetic.main.activity_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class CartActivity : AppCompatActivity(), CartContract.View, CartAdapter.CartAdapterListener {

  override lateinit var presenter: CartContract.Presenter
  private val adapter = CartAdapter(mutableListOf(), this)

  companion object {
    fun newIntent(context: Context) = Intent(context, CartActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cart)

    presenter = Injection.provideCartPresenter(this)

    title = getString(R.string.cart_title)

    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    cartRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    cartRecyclerView.adapter = adapter
  }

  override fun onResume() {
    super.onResume()
    presenter.start()
    EventBus.getDefault().register(this)
  }

  override fun onPause() {
    super.onPause()
    EventBus.getDefault().unregister(this)
  }

  override fun showCart(items: List<Food>, notify: Boolean) {
    adapter.updateItems(items, notify)
    if (items.isEmpty()) {
      emptyLabel.visibility = View.VISIBLE
      cartRecyclerView.visibility = View.INVISIBLE
      checkoutButton.isEnabled = false
    } else {
      emptyLabel.visibility = View.INVISIBLE
      cartRecyclerView.visibility = View.VISIBLE
      checkoutButton.isEnabled = true
    }
  }

  override fun removeItem(item: Food) {
    presenter.removeItem(item)
  }

  @Suppress("UNUSED_PARAMETER")
  fun showPaymentMethods(view: View) {
    animateShowPaymentMethodContainer()
  }

  @Suppress("UNUSED_PARAMETER")
  fun closePaymentMethods(view: View) {
    animateHidePaymentMethodContainer()
  }

  @Suppress("UNUSED_PARAMETER")
  fun paymentMethodSelected(view: View) {
    startActivity(CheckoutActivity.newIntent(this))
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCartDeleteItemEvent(event: CartDeleteItemEvent) {
    adapter.notifyItemRemoved(event.position)
    presenter.loadCart(false)
  }

  private fun animateShowPaymentMethodContainer() {
    val clipInfo = PaymentMethodClipInfo()
    val anim = ViewAnimationUtils.createCircularReveal(paymentMethodContainer, clipInfo.x, clipInfo.y, 0f, clipInfo.radius)
    paymentMethodContainer.visibility = View.VISIBLE
    anim.start()
  }

  private fun animateHidePaymentMethodContainer() {
    val clipInfo = PaymentMethodClipInfo()
    val anim = ViewAnimationUtils.createCircularReveal(paymentMethodContainer, clipInfo.x, clipInfo.y, clipInfo.radius, 0f)
    anim.addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        paymentMethodContainer.visibility = View.INVISIBLE
      }
    })
    anim.start()
  }

  private inner class PaymentMethodClipInfo {
    val x = paymentMethodContainer.width / 2
    val y = paymentMethodContainer.height - checkoutButton.height
    val radius = Math.hypot(x.toDouble(), y.toDouble()).toFloat()
  }
}
