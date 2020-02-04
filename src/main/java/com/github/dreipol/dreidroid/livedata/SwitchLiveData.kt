package com.github.dreipol.dreidroid.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


fun <B> switchLiveData(switchSource: LiveData<Boolean>, sourceIfTrue: LiveData<B>, sourceIfFalse: LiveData<B>): LiveData<B> {
    val mergedLiveData = MediatorLiveData<B>()
    mergedLiveData.addSource(switchSource) { switch ->
        mergedLiveData.value = if (switch) sourceIfTrue.value else sourceIfFalse.value
    }
    mergedLiveData.addSource(sourceIfTrue) { sourceIfTrueValue ->
        mergedLiveData.value = if (switchSource.value == true) sourceIfTrueValue else sourceIfFalse.value
    }
    mergedLiveData.addSource(sourceIfFalse) { sourceIfFalseValue ->
        mergedLiveData.value = if (switchSource.value == true) sourceIfTrue.value else sourceIfFalseValue
    }
    return mergedLiveData
}
