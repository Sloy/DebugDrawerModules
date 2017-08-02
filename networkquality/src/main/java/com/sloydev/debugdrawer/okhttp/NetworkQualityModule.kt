package com.sloydev.debugdrawer.okhttp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import com.sloydev.debugdrawer.common.DebugModuleAdapter
import com.sloydev.debugdrawer.common.onItemSelected

class NetworkQualityModule(context: Context) : DebugModuleAdapter() {

    companion object {
        private val DELAY_VALUES = arrayOf(0.ms, 500.ms, 1000.ms, 2000.ms, 3000.ms, 5000.ms, 10000.ms)
        private val ERROR_VALUES = arrayOf(0.pct, 3.pct, 10.pct, 25.pct, 50.pct, 100.pct)

        @JvmStatic
        fun interceptor(context: Context) = NetworkQualityInterceptor(NetworkQualityConfig(context))
    }


    private val networkQualityConfig = NetworkQualityConfig(context)
    private lateinit var enabledSwitch: Switch
    private lateinit var delaySpinner: Spinner
    private lateinit var errorSpinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val moduleView = inflater.inflate(R.layout.dd_module_networkquality, parent, false)

        setupEnabledSwitch(moduleView)
        setupDelaySpinner(moduleView)
        setupErrorSpinner(moduleView)

        return moduleView
    }

    private fun setupEnabledSwitch(moduleView: View) {
        enabledSwitch = moduleView.findViewById<Switch>(R.id.dd_module_networkquality_enabled)
        enabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            networkQualityConfig.networkEnabled = isChecked
        }
    }

    private fun setupDelaySpinner(moduleView: View) {
        delaySpinner = moduleView.findViewById<Spinner>(R.id.debug_network_delay)
        val delayAdapter = ArrayAdapter<DelayMs>(moduleView.context, R.layout.dd_module_networkquality_spinner_item, DELAY_VALUES)
        delayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        delaySpinner.adapter = delayAdapter
        delaySpinner.onItemSelected { position ->
            networkQualityConfig.delayMs = delayAdapter.getItem(position).value
        }
    }

    private fun setupErrorSpinner(moduleView: View) {
        errorSpinner = moduleView.findViewById<Spinner>(R.id.debug_network_error)
        val errorAdapter = ArrayAdapter<ErrorPct>(moduleView.context, R.layout.dd_module_networkquality_spinner_item, ERROR_VALUES)
        errorAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        errorSpinner.adapter = errorAdapter
        errorSpinner.onItemSelected { position ->
            networkQualityConfig.errorPercentage = errorAdapter.getItem(position).value
        }
    }

    override fun onResume() {
        enabledSwitch.isChecked = networkQualityConfig.networkEnabled
        delaySpinner.setSelection(DELAY_VALUES.indexOf(networkQualityConfig.delayMs.ms))
        errorSpinner.setSelection(ERROR_VALUES.indexOf(networkQualityConfig.errorPercentage.pct))
    }
}
