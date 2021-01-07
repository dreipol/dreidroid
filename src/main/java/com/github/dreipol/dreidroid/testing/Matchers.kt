package com.github.dreipol.dreidroid.testing

import android.view.View
import android.widget.CheckBox
import androidx.annotation.StringRes
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

/**
 * Checks if [TextInputLayout] shows provided text as error.
 *
 * @param matcherText text which should be displayed as error
 */
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

/**
 * Checks if [TextInputLayout] shows provided text as error.
 *
 * @param matcherTextResource text resource which should be displayed as error
 */
fun textInputLayoutWithError(@StringRes matcherTextResource: Int): BoundedMatcher<View?, TextInputLayout>? {
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with error")
        }

        override fun matchesSafely(editTextField: TextInputLayout): Boolean {
            return editTextField.context.getString(matcherTextResource) == editTextField.error.toString()
        }
    }
}

/**
 * Checks if [TextInputLayout] shows any error.
 *
 * Attention: if error is set to an empty string, it will be set to null and therefore this check will fail
 */
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

/**
 * Checks if [CheckBox] shows any error.
 *
 * Attention: if error is set to an empty string, it will be set to null and therefore this check will fail
 */
fun checkboxWithError(): BoundedMatcher<View?, CheckBox>? {
    return object : BoundedMatcher<View?, CheckBox>(CheckBox::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with non empty error")
        }

        override fun matchesSafely(checkBox: CheckBox): Boolean {
            return checkBox.error != null
        }
    }
}

/**
 * Checks if [TextInputLayout] doesn't show any error
 */
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