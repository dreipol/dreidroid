package com.github.dreipol.dreidroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
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
     * @param delay delay in millisecond after which the effect is taking place
     */
    @SuppressLint("ClickableViewAccessibility")
    public fun useTouchDownListener(touchDownListenerView: View, touchDownAlphaView: View, delay: Long = 200L) {
        val handler = Handler(Looper.getMainLooper())
        val reduceAlphaTask = Runnable { touchDownAlphaView.alpha = 0.5f }
        touchDownListenerView.setOnTouchListener { _, motionEvent: MotionEvent ->
            val boundingRectangleOfTouchDownAlphaView =
                Rect(touchDownListenerView.left, touchDownListenerView.top, touchDownListenerView.right,
                    touchDownListenerView.bottom)
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    handler.postDelayed(reduceAlphaTask, delay)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacks(reduceAlphaTask)
                    touchDownAlphaView.alpha = 1f
                }
                MotionEvent.ACTION_MOVE -> if (!boundingRectangleOfTouchDownAlphaView.contains(
                        touchDownListenerView.left + motionEvent.x.toInt(), touchDownListenerView.top + motionEvent.y
                            .toInt())) {
                    handler.removeCallbacks(reduceAlphaTask)
                    touchDownAlphaView.alpha = 1f
                } else {
                    handler.removeCallbacks(reduceAlphaTask)
                    handler.postDelayed(reduceAlphaTask, delay)
                }
            }
            false
        }
    }
}
