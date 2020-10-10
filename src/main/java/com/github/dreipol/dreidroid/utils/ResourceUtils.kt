package com.github.dreipol.dreidroid.utils

import android.content.Context

fun dp2px(dp: Int, context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5).toInt()
}