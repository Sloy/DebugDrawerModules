package com.sloydev.debugdrawer.okhttp

import android.content.Context
import com.sloydev.debugdrawer.common.Preferences

class NetworkQualityConfig(context: Context) : Preferences(context) {
    var networkEnabled by booleanPref(defaultValue = true)
    var delayMs by intPref(defaultValue = 0)
    var errorPercentage by intPref(defaultValue = 0)
}