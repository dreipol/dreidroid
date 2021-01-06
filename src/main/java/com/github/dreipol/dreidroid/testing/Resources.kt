package com.github.dreipol.dreidroid.testing

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Helper to get resource string during unit testing
 */
public fun getResourceString(id: Int): String {
    val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    return targetContext.resources.getString(id)
}