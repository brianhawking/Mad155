package com.example.topactionbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Item1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item1)

        title = "Item 1"

        // my_child_toolbar is defined in the layout file
        setSupportActionBar(findViewById(R.id.my_toolbar))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}