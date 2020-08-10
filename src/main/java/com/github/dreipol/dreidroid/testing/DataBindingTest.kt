package com.github.dreipol.dreidroid.testing

import android.app.Activity
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.rule.ActivityTestRule
import org.junit.Before

/**
 * Base class for unit tests that interact with databinding fragments. This is needed to ensure
 * that espresso waits until all databinding updates are resolved before executing matchers.
 */
open class DataBindingTest<T: Activity>(activityClass: Class<T>) {
    private var activityScenarioRule = ActivityTestRule(activityClass)
    private lateinit var dataBindingIdlingResource: IdlingResource

    @Before
    fun registerIdlingResources() {
        dataBindingIdlingResource =
            DataBindingIdlingResource(activityScenarioRule)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }
}