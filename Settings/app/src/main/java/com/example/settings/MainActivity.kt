package com.example.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class MainActivity : AppCompatActivity() {

    lateinit var theme: TextView
    val PREF_NAME = "settings"
    val PREF_DARK_THEME = "dark_theme"
    val PREF_CURRENCY = "currency"

    override fun onCreate(savedInstanceState: Bundle?) {

        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        var useDarkTheme = SP.getBoolean(PREF_DARK_THEME, false)
        var currency = SP.getString(PREF_CURRENCY, "$")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switch: Switch = findViewById(R.id.switch1)
        theme = findViewById(R.id.theme)

        switch.isChecked = useDarkTheme

        if (useDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            theme.text = "Dark"
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            theme.text = "Light"
        }

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            toggleTheme(isChecked)
        }
    }

    private fun toggleTheme(isChecked: Boolean) {
        val SP = getSharedPreferences("settings", MODE_PRIVATE).edit()
        SP.apply {
            putBoolean(PREF_DARK_THEME, isChecked)
            apply()
            println(isChecked)
        }
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            theme.text = "Dark"
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            theme.text = "Light"
        }
    }
}