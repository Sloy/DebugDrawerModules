package com.sloydev.debugdrawer.okhttp

internal data class DelayMs(val value: Int) {
    override fun toString(): String {
        return when {
            value == 0 -> "0 s"
            value % 1000 == 0 -> "${value/1000} s"
            else -> "${value.toFloat()/1000} s"
        }
    }
}
internal val Int.ms: DelayMs
    get() = DelayMs(this)

