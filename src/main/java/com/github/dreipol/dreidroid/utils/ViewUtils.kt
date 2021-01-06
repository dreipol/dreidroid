package com.github.dreipol.dreidroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View


public object ViewUtils {
    /**
     * Converts dp to Pixels
     *
     * @param dp value to convert in dp
     * @return value in Pixels
     */
    public fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }


    /**
     * Sets a touchdown effect
     *
     * For the effect, the alpha value of the view is set to 0.5
     *
     * @param touchDownListenerView view on which the touchdown listener is attached
     * @param touchDownAlphaView view on which the alpha value is set
     */
    @SuppressLint("ClickableViewAccessibility")
    public fun useTouchDownListener(touchDownListenerView: View, touchDownAlphaView: View) {
        touchDownListenerView.setOnTouchListener { _, motionEvent: MotionEvent ->
            val boundingRectangleOfTouchDownAlphaView =
                Rect(touchDownListenerView.left, touchDownListenerView.top, touchDownListenerView.right,
                    touchDownListenerView.bottom)
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchDownAlphaView.alpha = 0.5f
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    touchDownAlphaView.alpha = 1f
                }
                MotionEvent.ACTION_MOVE -> if (!boundingRectangleOfTouchDownAlphaView.contains(
                        touchDownListenerView.left + motionEvent.x.toInt(), touchDownListenerView.top + motionEvent.y
                            .toInt())) {
                    touchDownAlphaView.alpha = 1f
                } else {
                    touchDownAlphaView.alpha = 0.5f
                }
            }
            false
        }
    }
}
