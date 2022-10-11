package com.example.mealtip

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.os.Bundle
import android.view.View
import android.widget.*

class Settings : AppCompatActivity() {

    val PREF_NAME = "settings"
    val PREF_DARK_THEME = "dark_theme"
    val PREF_CURRENCY = "currency"
    var PREF_IMAGE = "image"

    override fun onCreate(savedInstanceState: Bundle?) {

        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        var useDarkTheme = SP.getBoolean(PREF_DARK_THEME, false)
        var currencyIcon = SP.getString(PREF_CURRENCY, "$")
        var useImage = SP.getBoolean(PREF_IMAGE, true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switch: Switch = findViewById(R.id.switch1)
        switch.isChecked = useDarkTheme

        val imageSwitch: Switch = findViewById(R.id.imageSwitch)
        imageSwitch.isChecked = useImage

        val currencyGroup: Spinner = findViewById(R.id.spinner)

        if (useDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            toggleTheme(isChecked)
        }

        imageSwitch.setOnCheckedChangeListener { view, isChecked ->
            toggleImage(isChecked)
        }

        val currencyArray = resources.getStringArray(R.array.currency)
        if (currencyGroup != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyArray)
            currencyGroup.adapter = adapter

            currencyGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val SP = getSharedPreferences("settings", MODE_PRIVATE).edit()
                    SP.apply {
                        putString(PREF_CURRENCY, currencyGroup.selectedItem.toString())
                        apply()
                        println(currencyGroup.selectedItem.toString())
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    println("Nothing")
                }

            }
        }
    }

    private fun toggleImage(isChecked: Boolean) {
        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
        SP.apply {
            putBoolean(PREF_IMAGE, isChecked)
            apply()
        }
        val image: ImageView = findViewById(R.id.imageView)
        if (isChecked) {
            image.visibility = View.VISIBLE
        } else {
            image.visibility = View.GONE
        }
    }

    private fun toggleTheme(isChecked: Boolean) {
        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
        SP.apply {
            putBoolean(PREF_DARK_THEME, isChecked)
            apply()
            println(isChecked)
        }
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}