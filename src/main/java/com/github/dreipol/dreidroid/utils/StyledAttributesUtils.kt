package com.github.dreipol.dreidroid.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

/**
 * Execute block safely with styled attributes
 */
fun withStyledAttributes(context: Context, attributes: AttributeSet, styleableId: IntArray, block: (styledAttributes: TypedArray) -> Unit) {
    val styledAttributes = context.obtainStyledAttributes(attributes, styleableId)
    try {
        block(styledAttributes)
    } finally {
        styledAttributes.recycle()
    }
}