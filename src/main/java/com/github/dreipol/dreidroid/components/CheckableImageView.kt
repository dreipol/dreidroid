package com.github.dreipol.dreidroid.components

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable

/**
 * Custom view to enable enable a checkbox that doesn't have text. Checked state should
 * be set through data binding.
 *
 * Example Usage:
 * ```
 * <com.github.dreipol.dreidroid.components.CheckableImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/checkbox"
        app:checked="@{viewModel.isCheckboxChecked}"
        android:onClick="@{() -> viewModel.isChecked = true}
    />
 * ```
 */
public class CheckableImageView(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatImageView(context, attributeSet), Checkable {

    private companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

    private var mChecked = false
    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState: IntArray = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) mergeDrawableStates(drawableState,
                                           CHECKED_STATE_SET)
        return drawableState
    }

    override fun setChecked(checked: Boolean) {
        if (mChecked != checked) {
            mChecked = checked
            refreshDrawableState()
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }
}