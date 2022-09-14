package com.example.topbarassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

//    lateinit var back_icon: ImageView
//    lateinit var menu_icon: ImageView
//    lateinit var appTitle: TextView
//    lateinit var toolbox: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        back_icon = findViewById(R.id.back_icon)
//        menu_icon = findViewById(R.id.menu_icon)
//        appTitle = findViewById(R.id.appTitle)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.items,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

    when (item.itemId) {
        R.id.item1 -> {
            Toast.makeText(this, "HI", Toast.LENGTH_LONG).show()
        }
    }

        return super.onOptionsItemSelected(item)
    }

}