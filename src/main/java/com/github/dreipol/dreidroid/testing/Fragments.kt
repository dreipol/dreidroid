package com.github.dreipol.dreidroid.testing

import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario

inline fun <reified T : Fragment> launchFragment(@StyleRes themeResId: Int, noinline builder: () -> T): FragmentScenario<T> {
    return FragmentScenario.launchInContainer(
        T::class.java,
        Bundle.EMPTY,
        themeResId,
        mockFragmentFactory(builder)
    )
}

inline fun <reified T : Fragment> mockFragmentFactory(noinline builder: () -> T): FragmentFactory {
    return object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                T::class.java -> builder()
                else -> super.instantiate(classLoader, className)
            }
        }
    }
}