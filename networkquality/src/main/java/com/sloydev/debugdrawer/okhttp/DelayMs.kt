package com.sloydev.debugdrawer.okhttp

internal data class DelayMs(val value: Int) {
    override fun toString(): String {
        return "$value ms"
    }
}
internal val Int.ms: DelayMs
    get() = DelayMs(this)

