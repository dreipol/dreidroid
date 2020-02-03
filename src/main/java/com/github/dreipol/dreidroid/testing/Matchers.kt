package com.github.dreipol.dreidroid.testing

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

fun textInputLayoutWithError(matcherText: String): BoundedMatcher<View?, TextInputLayout>? {
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with error: $matcherText")
        }

        override fun matchesSafely(editTextField: TextInputLayout): Boolean {
            return matcherText == editTextField.error.toString()
        }
    }
}

fun textInputLayoutWithError(): BoundedMatcher<View?, TextInputLayout>? {
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with non empty error")
        }

        override fun matchesSafely(editTextField: TextInputLayout): Boolean {
            return editTextField.error != null
        }
    }
}

fun textInputLayoutWithoutError(): BoundedMatcher<View?, TextInputLayout>? {
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("without error")
        }

        override fun matchesSafely(editTextField: TextInputLayout): Boolean {
            return editTextField.error == null
        }
    }
}