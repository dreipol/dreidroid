package com.github.dreipol.dreidroid.utils

import android.view.animation.Animation

object AnimationHelper {

    private var enterAnimation: Animation? = null
    private var exitAnimation: Animation? = null

    fun overrideNextFragmentEnterAnimation(animation: Animation) {
        enterAnimation = animation
    }

    fun overrideNextFragmentExitAnimation(animation: Animation) {
        exitAnimation = animation
    }

    fun getNextFragmentEnterAnimation(): Animation? {
        val currentAnimation = enterAnimation
        enterAnimation = null
        return currentAnimation
    }

    fun getNextFragmentExitAnimation(): Animation? {
        val currentAnimation = exitAnimation
        exitAnimation = null
        return currentAnimation
    }
}