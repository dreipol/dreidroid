package com.github.dreipol.dreidroid.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

public class BaseViewModelFactory<T>(internal val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

public inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    val factory = viewModelFactoryFromCreator(creator) ?: this.defaultViewModelProviderFactory
    return ViewModelProvider(this, factory).get(T::class.java)
}

public inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    val factory = viewModelFactoryFromCreator(creator) ?: this.defaultViewModelProviderFactory
    return ViewModelProvider(this, factory).get(T::class.java)
}

public fun <T : ViewModel> viewModelFactoryFromCreator(creator: (() -> T)? = null): BaseViewModelFactory<T>? =
    if (creator == null) null else BaseViewModelFactory(creator)