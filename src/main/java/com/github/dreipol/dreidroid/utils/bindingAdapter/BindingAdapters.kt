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
 * Exposes the attribute "app:onNavigationItemSelected" for [BottomNavigationView] to use it with viewBinding
 *
 * @param onNavigationItemSelected execute when navigation item was selected
 */
@BindingAdapter("onNavigationItemSelected")
fun setOnNavigationItemSelected(bottomNavigationView: BottomNavigationView,
    onNavigationItemSelected: BottomNavigationView.OnNavigationItemSelectedListener) {
    bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelected)
}

/**
 * Exposes the attribute "app:onNavigationClicked" for [Toolbar] to use it with viewBinding
 *
 * @param navigator execute after navigation icon has been clicked
 */
@BindingAdapter("onNavigationClicked")
fun onNavigationClicked(toolbar: Toolbar, navigator: Runnable) {
    toolbar.setNavigationOnClickListener { navigator.run() }
}

/**
 * Exposes the attribute "app:showNavigationIcon" for [Toolbar] to use it with viewBinding
 *
 * @param showNavigationIcon specifies it the icon should be shown or not
 */
@BindingAdapter("showNavigationIcon")
fun setShowNavigationIcon(toolbar: Toolbar, showNavigationIcon: Boolean) {
    if (!showNavigationIcon) {
        toolbar.navigationIcon = null
    }
}

/**
 * Exposes the attribute "app:selectedItem" for [BottomNavigationView] to use it with viewBinding
 *
 * @param selectedMenuItemResource menu item id to select
 */
@BindingAdapter("selectedItem")
fun setSelectedItem(bottomNavigationView: BottomNavigationView, selectedMenuItemResource: Int) {
    if (bottomNavigationView.selectedItemId != selectedMenuItemResource) {
        bottomNavigationView.selectedItemId = selectedMenuItemResource
    }
}

/**
 * Exposes the attribute "app:adapter" for [RecyclerView] to use it with viewBinding
 *
 * @param adapter adapter to set for recycler view
 */
@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    recyclerView.adapter = adapter
}

/**
 * Exposes the attribute "android:adapter" for [View] to use it with viewBinding
 *
 * @param resource dimen resource id which is used to set the elevation
 */
@BindingAdapter("android:elevation")
fun setElevation(view: View, resource: Int) {
    view.elevation = view.context.resources.getDimension(resource)
}

/**
 * Exposes the attribute "android:src" for [ImageView] to use it with viewBinding
 *
 * @param resource resource id which is used to set the image
 */
@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

/**
 * Exposes the attribute "app:optionalTextResource" for [TextView] to use it with viewBinding
 *
 * @param resource nullable resource id which is used to set the text
 */
@BindingAdapter("optionalTextResource")
fun setOptionalTextResoure(textView: TextView, resource: Int?) {
    resource?.let {
        textView.setText(it)
    }
}

/**
 * Exposes the attribute "app:optionalText" for [TextView] to use it with viewBinding
 *
 * @param text nullable string which is used to set the text
 */
@BindingAdapter("optionalText")
fun setOptionalText(textView: TextView, text: String?) {
    text?.let {
        textView.text = it
    }
}

/**
 * Exposes the attribute "app:progessBarColor" for [ProgressBar] to use it with viewBinding
 *
 * If called, the attribute [ProgressBar.isIndeterminate] is set to false
 * and the colorFilter of the indeterminateDrawable is set to the given color
 *
 * @param color color resource id which is used to set the color
 */
@BindingAdapter("progessBarColor")
fun setProgessBarColor(progressBar: ProgressBar, color: Int) {
    progressBar.isIndeterminate = true
    progressBar.indeterminateDrawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
}

/**
 * Exposes the attribute "android:visibility" for [View] to use it with viewBinding
 *
 * @param visible if false, the visibility is set to GONE, else to VISIBLE
 */
@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

/**
 * Exposes the attribute "app:disableBackground" for [View] to use it with viewBinding
 *
 * @param disableBackground if true, the background is set to null
 */
@BindingAdapter("disableBackground")
fun disableBackground(view: View, disableBackground: Boolean) {
    if (disableBackground) {
        view.background = null
    }
}

/**
 * Overrides the attribute "android:onClick" for [View] to use it with viewBinding
 *
 * @param action runnable to execute on click
 */
@BindingAdapter("android:onClick")
fun onViewClick(view: View, action: Runnable?) {
    view.setOnClickListener { action?.run() }
}

// -------------
// Bidirectional-Bindings
// -------------

// Tab-View Selected Tab
/**
 * Setter for the bidirectional binding attribute "app:currentTab" for [ViewPager]
 *
 * @param newTab tab index to which the current tab is set
 */
@BindingAdapter("currentTab")
fun setNewTab(pager: ViewPager, newTab: Int) {
    // don't forget to break possible infinite loops!
    if (pager.currentItem != newTab) {
        pager.setCurrentItem(newTab, true)
    }
}

/**
 * Getter for the bidirectional binding attribute "app:currentTab" for [ViewPager]
 *
 * @return the current selected tab index
 */
@InverseBindingAdapter(attribute = "currentTab", event = "currentTabAttrChanged")
fun getCurretTab(pager: ViewPager): Int {
    return pager.currentItem
}

/**
 * Sets the [InverseBindingListener] for the bidirectional binding attribute "app:currentTab" for [ViewPager]
 */
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
/**
 * Setter for the bidirectional binding attribute "app:isRefreshing" for [SwipeRefreshLayout]
 *
 * @param isRefreshing value to set
 */
@BindingAdapter("isRefreshing")
fun setIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    if (swipeRefreshLayout.isRefreshing != isRefreshing) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }
}

/**
 * Getter for the bidirectional binding attribute "app:isRefreshing" for [SwipeRefreshLayout]
 *
 * @return current refreshing state
 */
@InverseBindingAdapter(attribute = "isRefreshing", event = "isRefreshingAttrChanged")
fun getIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean {
    return swipeRefreshLayout.isRefreshing
}

/**
 * Sets the [InverseBindingListener] for the bidirectional binding attribute "app:isRefreshing" for [SwipeRefreshLayout]
 */
@BindingAdapter("isRefreshingAttrChanged")
fun setIsRefreshingListener(swipeRefreshLayout: SwipeRefreshLayout, attrChange: InverseBindingListener) {
    swipeRefreshLayout.setOnRefreshListener {
        attrChange.onChange()
    }
}