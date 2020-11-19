package com.github.dreipol.dreidroid.utils

import android.content.Context

object ViewUtils {
    fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}