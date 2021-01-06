package com.github.dreipol.dreidroid.livedata

/**
 * A class witch allows in combination with LiveData the observer to check if the new value was already handled
 *
 * [See Also](https://android.jlelse.eu/sending-events-from-viewmodel-to-activities-fragments-the-right-way-26bb68502b24)
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}