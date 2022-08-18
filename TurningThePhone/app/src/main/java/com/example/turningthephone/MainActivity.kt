package com.example.turningthephone

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Landscape or Portrait"

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)
        val name = findViewById<EditText>(R.id.editText_name)

        button.setOnClickListener {
            if (button.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                textView.text = String.format(resources.getString(R.string.textMessage, name.text, "portrait"))
            } else {
                textView.text = String.format(resources.getString(R.string.textMessage, name.text, "landscape"))
            }
        }
    }
}