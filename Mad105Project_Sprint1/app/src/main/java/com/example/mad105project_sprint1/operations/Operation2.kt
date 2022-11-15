package com.example.mad105project_sprint1.operations

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mad105project_sprint1.R

class Operation2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = conversion(sp.getInt("theme", R.style.Theme_0))
        setTheme(theme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation2)
    }

    private fun conversion(theme: Int): Int {
        return when (theme) {
            R.style.Theme_0 -> R.style.Theme_0_AppCompat_Translucent
            R.style.Theme_1 -> R.style.Theme_1_AppCompat_Translucent
            R.style.theme_3 -> R.style.Theme_3_AppCompat_Translucent
            R.style.theme_4 -> R.style.Theme_4_AppCompat_Translucent
            R.style.theme_5 -> R.style.Theme_5_AppCompat_Translucent
            R.style.theme_6 -> R.style.Theme_6_AppCompat_Translucent
            R.style.theme_7 -> R.style.Theme_7_AppCompat_Translucent
            else -> R.style.Theme_0_AppCompat_Translucent
        }
    }
}