package com.github.dreipol.dreidroid.utils

import android.view.animation.AlphaAnimation
import android.view.animation.Animation

object AnimationHelper {

    private var enterAnimation: Animation? = null
    private var exitAnimation: Animation? = null
    private var overrideNextEnterAnimation = false
    private var overrideNextExitAnimation = false
    private val noAnimation
        get() = AlphaAnimation(0f, 0f)

    fun overrideNextFragmentEnterAnimation(animation: Animation?) {
        overrideNextEnterAnimation = true
        enterAnimation = animation
    }

    fun overrideNextFragmentExitAnimation(animation: Animation?) {
        overrideNextExitAnimation = true
        exitAnimation = animation
    }

    fun shouldOverrideAnimation(enter: Boolean): Boolean {
        if (enter) {
            return overrideNextEnterAnimation
        }
        return overrideNextExitAnimation
    }

    fun getNextFragmentEnterAnimation(): Animation {
        overrideNextEnterAnimation = false
        val currentAnimation = enterAnimation
        enterAnimation = null
        return currentAnimation ?: noAnimation
    }

    fun getNextFragmentExitAnimation(): Animation {
        overrideNextExitAnimation = false
        val currentAnimation = exitAnimation
        exitAnimation = null
        return currentAnimation ?: noAnimation
    }
}