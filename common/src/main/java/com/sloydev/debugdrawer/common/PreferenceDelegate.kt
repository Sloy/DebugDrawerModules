package com.sloydev.debugdrawer.common

import android.content.SharedPreferences
import kotlin.reflect.KProperty


abstract class PrefDelegate<T>(val prefsFile: SharedPreferences, val prefKey: String?) {


    abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}

class StringPrefDelegate(prefsFile: SharedPreferences,
                         prefKey: String? = null,
                         val defaultValue: String? = null)
    : PrefDelegate<String?>(prefsFile, prefKey) {

    override fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return prefsFile.getString(prefKey ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        prefsFile.edit().putString(prefKey ?: property.name, value).apply()
    }
}

class BooleanPrefDelegate(prefsFile: SharedPreferences,
                          prefKey: String? = null,
                          val defaultValue: Boolean = false)
    : PrefDelegate<Boolean>(prefsFile, prefKey) {

    override fun getValue(thisRef: Any?, property: KProperty<*>) = prefsFile.getBoolean(prefKey ?: property.name, defaultValue)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        prefsFile.edit().putBoolean(prefKey ?: property.name, value).apply()
    }
}


class IntPrefDelegate(prefsFile: SharedPreferences,
                      prefKey: String? = null,
                      val defaultValue: Int = 0)
    : PrefDelegate<Int>(prefsFile, prefKey) {
    override fun getValue(thisRef: Any?, property: KProperty<*>) = prefsFile.getInt(prefKey ?: property.name, defaultValue)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        prefsFile.edit().putInt(prefKey ?: property.name, value).apply()
    }
}