package com.github.dreipol.dreidroid.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * Can be used to increase the touch-size of its wrapped child
 *
 * Example:
 * '''
 * <com.github.dreipol.dreidroid.components.TouchSizeIncreaser
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:padding="8dp">
        <Spinner
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"/>
    </ch.tutti.ui.util.TouchSizeIncreaser>
 * '''
 */
public class TouchSizeIncreaser(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        getChildAt(0)?.onTouchEvent(event)
        return true
    }
}
