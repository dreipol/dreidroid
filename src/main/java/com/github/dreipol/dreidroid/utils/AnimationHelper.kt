package com.github.dreipol.dreidroid.utils

import android.view.animation.AlphaAnimation
import android.view.animation.Animation

/**
 * Helper to use for overriding animations in fragments.
 *
 * An example, where this is used to fix an animation bug in the androidx navigation, can be found
 * [here](https://blog.dreipol.ch/android-jetpack-architecture-ffd1f89fbf47)
 */
public object AnimationHelper {

    private var enterAnimation: Animation? = null
    private var exitAnimation: Animation? = null
    private var overrideNextEnterAnimation = false
    private var overrideNextExitAnimation = false
    private val noAnimation
        get() = AlphaAnimation(0f, 0f)

    /**
     * Sets animation which is used when next enter animation is overridden
     */
    public fun overrideNextFragmentEnterAnimation(animation: Animation?) {
        overrideNextEnterAnimation = true
        enterAnimation = animation
    }

    /**
     * Sets animation which is used when next enter animation is overridden
     */
    public fun overrideNextFragmentExitAnimation(animation: Animation?) {
        overrideNextExitAnimation = true
        exitAnimation = animation
    }

    /**
     * Returns weather the next animation should be overridden or not
     *
     * @param enter specifies if the enter or exit animation is meant
     */
    public fun shouldOverrideAnimation(enter: Boolean): Boolean {
        if (enter) {
            return overrideNextEnterAnimation
        }
        return overrideNextExitAnimation
    }

    /**
     * Returns the set enter animation and resets it to null afterwards
     */
    public fun getNextFragmentEnterAnimation(): Animation {
        overrideNextEnterAnimation = false
        val currentAnimation = enterAnimation
        enterAnimation = null
        return currentAnimation ?: noAnimation
    }

    /**
     * Returns the set exit animation and resets it to null afterwards
     */
    public fun getNextFragmentExitAnimation(): Animation {
        overrideNextExitAnimation = false
        val currentAnimation = exitAnimation
        exitAnimation = null
        return currentAnimation ?: noAnimation
    }
}
