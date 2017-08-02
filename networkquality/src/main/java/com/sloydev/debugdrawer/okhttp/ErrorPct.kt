package com.sloydev.debugdrawer.okhttp

internal data class ErrorPct(val value: Int) {
    override fun toString(): String {
        return "$value%"
    }
}

internal val Int.pct: ErrorPct
    get() = ErrorPct(this)

