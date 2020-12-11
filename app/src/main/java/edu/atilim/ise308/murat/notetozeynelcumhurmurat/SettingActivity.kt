package edu.atilim.ise308.murat.notetozeynelcumhurmurat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch

class SettingActivity : AppCompatActivity() {
    private var showDividers: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val prefers = getSharedPreferences("NoteToZeynelCumhurMurat", Context.MODE_PRIVATE)
        showDividers = prefers.getBoolean("dividers", true)
        val setting_switch = findViewById<Switch>(R.id.setting_switch)
        setting_switch.isChecked = showDividers
        setting_switch.setOnCheckedChangeListener { button, isChecked ->
            showDividers = isChecked
        }
    }
    override fun onPause() {
        super.onPause()
        val prefers = getSharedPreferences("NoteToZeynelCumhurMurat", Context.MODE_PRIVATE)
        val editor = prefers.edit()
        editor.putBoolean("dividers",showDividers)
        editor.apply()
    }
}