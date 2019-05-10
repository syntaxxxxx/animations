package com.syntax.android.foodmart.ui.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.syntax.android.foodmart.R
import com.syntax.android.foodmart.ui.Injection
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity(), CategoriesContract.View {

  override lateinit var presenter: CategoriesContract.Presenter

  companion object {
    fun newIntent(context: Context) =
      Intent(context, CategoriesActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_categories)

    presenter = Injection.provideCategoriesPresenter(this)

    title = getString(R.string.categories)

    tabLayout.setupWithViewPager(viewPager)
  }

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun showCategories(categories: List<String>) {
    viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
      override fun getItem(position: Int): Fragment =
          CategoryFragment.newInstance(categories[position])

      override fun getCount() = categories.size

      override fun getPageTitle(position: Int) = categories[position]
    }

    viewPager.setPageTransformer(true, DepthPageTransformer())
  }
}
