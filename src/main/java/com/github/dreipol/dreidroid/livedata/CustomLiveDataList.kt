package com.github.dreipol.dreidroid.livedata

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData

/**
 * Live data that listens on changes all property changes of all elements of the provided list
 */
public class CustomLiveDataList<T : BaseObservable>(initial: List<T>?) : MutableLiveData<List<T>>(initial) {
    private val propertyChangedCallback: Observable.OnPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            value?.let { setValue(it) }
        }
    }

    private var isActive: Boolean = false

    override fun setValue(newValue: List<T>) {
        if (isActive) {
            value?.let {
                it.forEach { item ->
                    item.removeOnPropertyChangedCallback(propertyChangedCallback)
                }
            }
        }

        super.setValue(newValue)
        if (isActive) {
            newValue.forEach { item ->
                item.addOnPropertyChangedCallback(propertyChangedCallback)
            }
        }
    }

    override fun onActive() {
        isActive = true
        value?.let {
            it.forEach { item ->
                item.addOnPropertyChangedCallback(propertyChangedCallback)
            }
        }
    }

    override fun onInactive() {
        isActive = false
        value?.let {
            it.forEach { item ->
                item.removeOnPropertyChangedCallback(propertyChangedCallback)
            }
        }
        super.onInactive()
    }
}