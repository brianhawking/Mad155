package com.example.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var classType: EditText
    lateinit var classNumber: EditText

    val PREF_NAME = "prefs"
    val PREF_DARK_THEME = "dark_theme"

    override fun onCreate(savedInstanceState: Bundle?) {


        val preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false)

        if (useDarkTheme) {
            setTheme(com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggle = findViewById<Switch>(R.id.switch1)
        toggle.isChecked = useDarkTheme

        toggle.setOnCheckedChangeListener { view, isChecked ->
            toggleTheme(isChecked)
        }

        name = findViewById(R.id.text1)
        classType = findViewById(R.id.text3)
        classNumber = findViewById(R.id.text4)
    }

    private fun toggleTheme(darkTheme: Boolean) {
        val editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
        editor.apply {
            putBoolean("PREF_DARK_THEME", darkTheme)
            apply()
        }

        val intent = intent
        finish()
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        val SP = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val key1 = SP.getString("name", "")
        val key2 = SP.getString("classType", "")
        val key3 = SP.getInt("classNumber", 0)

        // assign them to the widgets

        name!!.setText(key1)
        classType!!.setText(key2)
        classNumber!!.setText(key3.toString())
    }

    override fun onPause() {
        super.onPause()

        val SP = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val myEdit = SP.edit()

        myEdit.putString("name", name!!.text.toString())
        myEdit.putString("classType", classType!!.text.toString())
        myEdit.putInt("classNumber", classNumber!!.text.toString().toInt())

        myEdit.apply()
    }
}