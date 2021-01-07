package com.github.dreipol.dreidroid.livedata

import io.realm.RealmObject

/**
 * Accessing attributes on an invalid Realm object throws an exception. This is especially bad when
 * the access happens in databinding evaluation as a the exception can not be caught and will
 * therefore lead to a crash. Accessing null objects in databinding is safe though therefore
 * we can just return null if the object is invalid.
 */
class SafeRealmObject<T : RealmObject>(originalObject: T) {
    var value: T? = originalObject
        get() {
            if (field?.isValid != false) {
                return field
            }
            return null
        }
}