package com.example.mad105project_sprint1

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.mad105project_sprint1.math.ColorScheme

class Settings : AppCompatActivity() {

    private var numberButtonIds = arrayOf(
        R.id.button0V2,
        R.id.button1,
        R.id.button2,
        R.id.button3,
        R.id.button4,
        R.id.button5,
        R.id.button6,
        R.id.button7,
        R.id.button8,
        R.id.button9,
        R.id.buttonDot,
        R.id.buttonLeft,
        R.id.buttonRight,
        R.id.buttonFrac
    )

    private var actionButtonIds = arrayOf(
        R.id.buttonDEL,
        R.id.buttonSubEqn,
        R.id.buttonAddEqn,
        R.id.buttonAddVar,
        R.id.buttonSubVar,
        R.id.buttonPlusMinus,
        R.id.setupMatrix,
        R.id.changeTheme
    )
    var index = 0

    private var themeStyles = arrayOf(
        R.style.Theme_0,
        R.style.Theme_1,
        R.style.theme_3,
        R.style.theme_4,
        R.style.theme_5,
        R.style.theme_6,
        R.style.theme_7
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = sp.getInt("theme", R.style.Theme_0)
        val index = sp.getInt("index", 0)
        setTheme(theme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val changeThemeButton = findViewById<Button>(R.id.changeTheme)
        val saveButton = findViewById<Button>(R.id.saveButton)
        changeThemeButton.setOnClickListener {
            this.index = (index + 1) % themeStyles.size
            val sp = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
            val myEdit = sp.edit()
            myEdit.putInt("theme", themeStyles[index])
            myEdit.putInt("index", this.index)
            myEdit.apply()


            val intent = intent
            finish()
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun getTheme(theme: Int): Int {
        return when (theme) {
            1 -> R.style.Theme_0
            2 -> R.style.Theme_1
            else -> R.style.Theme_0
        }
    }
}

