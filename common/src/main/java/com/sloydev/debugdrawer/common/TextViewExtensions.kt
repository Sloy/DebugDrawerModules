package com.sloydev.debugdrawer.common

import android.support.annotation.AttrRes
import android.util.TypedValue
import android.widget.TextView

fun TextView.setThemeColor(@AttrRes colorAttr: Int) {
    val typedValue = TypedValue()

    val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(colorAttr))
    val color = a.getColor(0, 0)

    a.recycle()

    this.setTextColor(color)

}