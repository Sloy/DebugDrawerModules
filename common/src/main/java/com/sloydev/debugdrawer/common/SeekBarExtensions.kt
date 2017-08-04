package com.sloydev.debugdrawer.common

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener



fun SeekBar.onChanged(listener: (Int) -> Unit) {
    setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            listener(progress)
        }

        override fun onStartTrackingTouch(p0: SeekBar) = Unit
        override fun onStopTrackingTouch(p0: SeekBar) = Unit

    })
}

fun SeekBar.preventDrawerTouch() {
    setOnTouchListener { _, _ ->
        parent.requestDisallowInterceptTouchEvent(true)
        false
    }
}