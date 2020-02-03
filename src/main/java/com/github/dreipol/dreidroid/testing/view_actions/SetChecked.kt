package com.github.dreipol.dreidroid.testing.view_actions

import android.view.View
import android.widget.Checkable
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matchers.isA


fun setChecked(checked: Boolean): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): BaseMatcher<View?> {
            return object : BaseMatcher<View?>() {
                override fun matches(item: Any): Boolean {
                    return isA(Checkable::class.java).matches(item)
                }

                override fun describeMismatch(item: Any?, mismatchDescription: Description?) {}
                override fun describeTo(description: Description?) {}
            }
        }

        override fun getDescription(): String {
            return "setting checked to '$checked'"
        }

        override fun perform(uiController: UiController?, view: View) {
            val checkableView = view as Checkable
            checkableView.isChecked = checked
        }
    }
}