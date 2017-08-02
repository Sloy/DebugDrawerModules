package com.sloydev.debugdrawer.preferences

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.processphoenix.ProcessPhoenix
import com.sloydev.debugdrawer.common.DebugModuleAdapter
import com.sloydev.preferator.Preferator
import java.io.File

class PreferencesModule private constructor(val clearOnly: List<SharedPreferences>? = null, val clearAll: Boolean = false)
    : DebugModuleAdapter() {

    companion object {
        fun justEdit() = PreferencesModule(clearOnly = null, clearAll = false)
        fun clearOnly(files: List<SharedPreferences>) = PreferencesModule(clearOnly = files)
        fun clearAll() = PreferencesModule(clearAll = true)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val moduleView = inflater.inflate(R.layout.dd_module_preferences, parent, false)

        moduleView.findViewById<View>(R.id.dd_module_preferences_edit).setOnClickListener {
            Preferator.launch(moduleView.context)
        }

        val clearButton = moduleView.findViewById<View>(R.id.dd_module_preferences_clear)
        if (!clearAll && clearOnly == null) {
            clearButton.visibility = View.GONE
        }
        clearButton.setOnClickListener {
            clearOnly?.forEach {
                it.edit().clear().commit()
            }

            if (clearAll) {
                findAllPreferences(moduleView.context).forEach {
                    it.edit().clear().commit()
                }
            }

            restartApplication(moduleView)
        }

        return moduleView
    }

    private fun findAllPreferences(context: Context): List<SharedPreferences> {
        val rootPath = context.applicationInfo.dataDir + "/shared_prefs"
        val prefsFolder = File(rootPath)
        return prefsFolder.list()
                ?.map { prefFileName ->
                    if (prefFileName.endsWith(".xml")) {
                        prefFileName.substring(0, prefFileName.indexOf(".xml"))
                    } else {
                        prefFileName
                    }
                }
                ?.map {
                    context.getSharedPreferences(it, Context.MODE_PRIVATE)
                } ?: emptyList<SharedPreferences>()

    }

    private fun restartApplication(moduleView: View) {
        Toast.makeText(moduleView.context, "Restarting app…", Toast.LENGTH_SHORT).show()
        moduleView.postDelayed({ ProcessPhoenix.triggerRebirth(moduleView.context) }, 200)
    }
}