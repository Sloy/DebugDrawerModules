package com.sloydev.debugdrawer.logs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.pedrovgs.lynx.LynxActivity
import com.readystatesoftware.chuck.Chuck
import com.readystatesoftware.chuck.ChuckInterceptor
import io.palaima.debugdrawer.base.DebugModule
import okhttp3.Interceptor

class LogsModule : DebugModule {

    companion object {
        @JvmStatic
        fun chuckInterceptor(context: Context): Interceptor {
            return ChuckInterceptor(context).showNotification(false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.dd_module_logs, parent, false)

        view.findViewById<View>(R.id.dd_module_logs_logcat).setOnClickListener {
            parent.context.startActivity(LynxActivity.getIntent(parent.context))
        }

        view.findViewById<View>(R.id.dd_module_logs_chuck).setOnClickListener {
            parent.context.startActivity(Chuck.getLaunchIntent(parent.context))
        }

        return view
    }

    override fun onOpened() = Unit
    override fun onResume() = Unit
    override fun onPause() = Unit
    override fun onClosed() = Unit
    override fun onStart() = Unit
    override fun onStop() = Unit
}