package com.sloydev.debugdrawer.common

import android.content.Context
import android.content.SharedPreferences

abstract class Preferences(context: Context,
                           val prefsFile: SharedPreferences = context.getSharedPreferences("debug_drawer_networkquality", Context.MODE_PRIVATE)) {

    fun booleanPref(prefKey: String? = null, defaultValue: Boolean = false) = BooleanPrefDelegate(prefsFile, prefKey, defaultValue)

    fun intPref(prefKey: String? = null, defaultValue: Int = 0) = IntPrefDelegate(prefsFile, prefKey, defaultValue)

}