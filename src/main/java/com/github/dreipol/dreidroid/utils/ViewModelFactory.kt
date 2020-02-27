package com.github.dreipol.dreidroid.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return ViewModelProviders.of(this, viewModelFactoryFromCreator(creator)).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return ViewModelProviders.of(this, viewModelFactoryFromCreator(creator)).get(T::class.java)
}

fun <T : ViewModel> viewModelFactoryFromCreator(creator: (() -> T)? = null): BaseViewModelFactory<T>? =
    if (creator == null) null else BaseViewModelFactory(creator)