package com.sloydev.debugdrawer.okhttp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import com.sloydev.debugdrawer.common.DebugModuleAdapter
import com.sloydev.debugdrawer.common.onChanged
import com.sloydev.debugdrawer.common.preventDrawerTouch
import com.sloydev.debugdrawer.common.setThemeColor


class NetworkQualityModule(context: Context) : DebugModuleAdapter() {

    companion object {
        private val DELAY_VALUES = arrayOf(0.ms, 500.ms, 1000.ms, 2000.ms, 3000.ms, 5000.ms, 10000.ms)
        private val ERROR_VALUES = arrayOf(0.pct, 3.pct, 10.pct, 25.pct, 50.pct, 100.pct)

        @JvmStatic
        fun interceptor(context: Context) = NetworkQualityInterceptor(NetworkQualityConfig(context))
    }


    private val networkQualityConfig = NetworkQualityConfig(context)
    private lateinit var enabledSwitch: Switch
    private lateinit var delaySeekBar: SeekBar
    private lateinit var errorSeekBar: SeekBar

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val moduleView = inflater.inflate(R.layout.dd_module_networkquality, parent, false)

        setupEnabledSwitch(moduleView)
        setupDelaySeekBar(moduleView)
        setupErrorSeekBar(moduleView)

        return moduleView
    }

    private fun setupEnabledSwitch(moduleView: View) {
        enabledSwitch = moduleView.findViewById<Switch>(R.id.dd_module_networkquality_enabled)
        enabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            networkQualityConfig.networkEnabled = isChecked
            delaySeekBar.isEnabled = isChecked
            errorSeekBar.isEnabled = isChecked
        }
    }

    private fun setupDelaySeekBar(moduleView: View) {
        val delayIndicator = moduleView.findViewById<TextView>(R.id.debug_network_delay_indicator)
        delaySeekBar = moduleView.findViewById<SeekBar>(R.id.debug_network_delay)
        delaySeekBar.preventDrawerTouch()
        delaySeekBar.max = DELAY_VALUES.size - 1
        delaySeekBar.onChanged { position ->
            networkQualityConfig.delayMs = DELAY_VALUES[position].value
            delayIndicator.text = DELAY_VALUES[position].toString()
            delayIndicator.setThemeColor(if (position == 0) android.R.attr.textColorSecondary else R.attr.colorAccent)
        }
    }

    private fun setupErrorSeekBar(moduleView: View) {
        val errorIndicator = moduleView.findViewById<TextView>(R.id.debug_network_error_indicator)
        errorSeekBar = moduleView.findViewById<SeekBar>(R.id.debug_network_error)
        errorSeekBar.preventDrawerTouch()
        errorSeekBar.max = ERROR_VALUES.size - 1
        errorSeekBar.onChanged { position ->
            networkQualityConfig.errorPercentage = ERROR_VALUES[position].value
            errorIndicator.text = ERROR_VALUES[position].toString()

            errorIndicator.setThemeColor(if (position == 0) android.R.attr.textColorSecondary else R.attr.colorAccent)
        }
    }

    override fun onResume() {
        enabledSwitch.isChecked = networkQualityConfig.networkEnabled
        delaySeekBar.progress = DELAY_VALUES.indexOf(networkQualityConfig.delayMs.ms)
        delaySeekBar.progress = DELAY_VALUES.indexOf(networkQualityConfig.delayMs.ms)
    }

}
