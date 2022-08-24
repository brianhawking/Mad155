package com.example.turningthephone

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var nameText: TextView
    lateinit var modeText: TextView
    lateinit var randomText: TextView
    lateinit var name: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "State Change"

        button = findViewById<Button>(R.id.button)
        nameText = findViewById<TextView>(R.id.name)
        modeText = findViewById<TextView>(R.id.mode)
        randomText = findViewById<TextView>(R.id.random)
        name = findViewById<EditText>(R.id.editText_name)

        button.setOnClickListener {
            nameText.text = String.format(resources.getString(R.string.name, name.text))
            randomText.text = ":LKJ:LSKDJFL:KJSDL:KFJS:LDKFJSD:LKFJ"

            if (button.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                modeText.text = String.format(resources.getString(R.string.mode,"portrait"))
            } else {
                modeText.text = String.format(resources.getString(R.string.mode, "landscape"))
            }
        }
        Log.d("DEBUG:", "onCreate()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name_text", nameText.text.toString())
        outState.putString("mode_text", modeText.text.toString())
        outState.putString("random_text", randomText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nameText.text = savedInstanceState.getString("name_text")
        modeText.text = savedInstanceState.getString("mode_text")
        randomText.text = savedInstanceState.getString("random_text")
    }

    override fun onStart() {
        super.onStart()
        Log.d("DEBUG:", "onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("DEBUG:", "onPause()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("DEBUG:", "onResume()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("DEBUG:", "onREStart()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("DEBUG:", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEBUG:", "onDestroy()")
    }
}