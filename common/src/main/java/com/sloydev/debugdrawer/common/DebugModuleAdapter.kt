package com.sloydev.debugdrawer.common

import io.palaima.debugdrawer.base.DebugModule

abstract class DebugModuleAdapter : DebugModule {
    override fun onOpened() = Unit
    override fun onResume() = Unit
    override fun onPause() = Unit
    override fun onClosed() = Unit
    override fun onStart() = Unit
    override fun onStop() = Unit
}