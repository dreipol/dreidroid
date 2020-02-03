package com.github.dreipol.dreidroid.testing

import android.app.Activity
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.rule.ActivityTestRule
import org.junit.Before

open class DataBindingTest<T: Activity>(activityClass: Class<T>) {
    private var activityScenarioRule = ActivityTestRule(activityClass)
    private lateinit var dataBindingIdlingResource: IdlingResource

    @Before
    fun registerIdlingResources() {
        dataBindingIdlingResource = DataBindingIdlingResource(activityScenarioRule)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }
}