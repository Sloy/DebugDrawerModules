package com.sloydev.debugdrawer.layer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.layer.sdk.LayerClient
import com.sloydev.debugdrawer.common.DebugModuleAdapter

class LayerDebugModule(private val layerClient: LayerClient) : DebugModuleAdapter() {

    private lateinit var debugLayerConnected: TextView
    private lateinit var debugLayerAuth: TextView
    private lateinit var debugLayerUser: TextView

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        val module = layoutInflater.inflate(R.layout.debug_layer, viewGroup, false)

        debugLayerConnected = module.findViewById<TextView>(R.id.debug_layer_connected)
        debugLayerAuth = module.findViewById<TextView>(R.id.debug_layer_auth)
        debugLayerUser = module.findViewById<TextView>(R.id.debug_layer_user)

        debugLayerConnected.setOnClickListener { onTextViewClicked(it) }
        debugLayerAuth.setOnClickListener { onTextViewClicked(it) }
        debugLayerUser.setOnClickListener { onTextViewClicked(it) }

        return module
    }

    override fun onOpened() {
        debugLayerConnected.text = getConnectedStatus()
        debugLayerAuth.text = getAuthStatus()
        debugLayerUser.text = getCurrentUser()
    }

    private fun getCurrentUser(): String {
        return layerClient.authenticatedUser
                ?.userId
                ?: "-"
    }

    private fun getConnectedStatus(): String {
        when {
            layerClient.isConnecting -> return "Connecting"
            layerClient.isConnected -> return "Connected"
            else -> return "Disconnected"
        }
    }

    private fun getAuthStatus(): String {
        if (layerClient.isAuthenticated) {
            return "Authenticated"
        } else {
            return "Not authenticated"
        }
    }

    private fun onTextViewClicked(v: View) {
        if (v is TextView) {
            val text = v.text.toString()
            val clipboard = v.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(text, text)
            clipboard.primaryClip = clip
            Toast.makeText(v.getContext(), "\"" + text + "\" copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}
