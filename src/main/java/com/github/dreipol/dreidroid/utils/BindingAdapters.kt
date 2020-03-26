package com.github.dreipol.dreidroid.utils

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toolbar
import androidx.databinding.*
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A collection of useable BindingAdapters
 */

@BindingMethods(
    value = [
        BindingMethod(
            type = BottomNavigationView::class,
            attribute = "onNavigationItemSelected",
            method = "setOnNavigationItemSelectedListener"
        )]
)
class TabBindingAdapter

@BindingAdapter("onNavigationClicked")
fun onNavigationClicked(toolbar: Toolbar, navigator: () -> Unit) {
    toolbar.setNavigationOnClickListener { navigator.invoke() }
}

@BindingAdapter("showNavigationIcon")
fun setShowNavigationIcon(toolbar: Toolbar, showNavigationIcon: Boolean) {
    if (!showNavigationIcon) {
        toolbar.navigationIcon = null
    }
}

@BindingAdapter("selectedItem")
fun setSelectedItem(bottomNavigationView: BottomNavigationView, selectedMenuItemResource: Int) {
    if (bottomNavigationView.selectedItemId != selectedMenuItemResource) {
        bottomNavigationView.selectedItemId = selectedMenuItemResource
    }
}

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    recyclerView.adapter = adapter
}

@BindingAdapter("android:elevation")
fun setElevation(view: View, resource: Int) {
    view.elevation = view.context.resources.getDimension(resource)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("optionalTextResource")
fun setOptionalTextResoure(textView: TextView, resource: Int?) {
    resource?.let {
        textView.setText(it)
    }
}

@BindingAdapter("optionalText")
fun setOptionalText(textView: TextView, text: String?) {
    text?.let {
        textView.text = it
    }
}

@BindingAdapter("progessBarColor")
fun setProgessBarColor(progressBar: ProgressBar, color: Int) {
    progressBar.isIndeterminate = true
    progressBar.indeterminateDrawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("disableBackground")
fun disableBackground(view: View, disableBackground: Boolean) {
    if (disableBackground) {
        view.background = null
    }
}

@BindingAdapter("android:onClick")
fun onViewClick(view: View, action: () -> Unit) {
    view.setOnClickListener { action.invoke() }
}

// -------------
// Bidirectional-Bindings
// -------------

// Tab-View Selected Tab
@BindingAdapter("currentTab")
fun setNewTab(pager: ViewPager, newTab: Int) {
    // don't forget to break possible infinite loops!
    if (pager.currentItem != newTab) {
        pager.setCurrentItem(newTab, true)
    }
}

@InverseBindingAdapter(attribute = "currentTab", event = "currentTabAttrChanged")
fun getCurretTab(pager: ViewPager): Int {
    return pager.currentItem
}

@BindingAdapter("currentTabAttrChanged")
fun setListeners(pager: ViewPager, attrChange: InverseBindingListener) {
    pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            attrChange.onChange()
        }
    })
}

// Swipe-Refresh state
@BindingAdapter("isRefreshing")
fun setIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    if (swipeRefreshLayout.isRefreshing != isRefreshing) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }
}

@InverseBindingAdapter(attribute = "isRefreshing", event = "isRefreshingAttrChanged")
fun getIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean {
    return swipeRefreshLayout.isRefreshing
}

@BindingAdapter("isRefreshingAttrChanged")
fun setIsRefreshingListener(swipeRefreshLayout: SwipeRefreshLayout, attrChange: InverseBindingListener) {
    swipeRefreshLayout.setOnRefreshListener {
        attrChange.onChange()
    }
}