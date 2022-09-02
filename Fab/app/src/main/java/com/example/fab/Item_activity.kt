package com.example.fab

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Item_activity : AppCompatActivity() {

    lateinit var itemView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        itemView = findViewById(R.id.itemView)

        val extras = intent.extras
        println(extras)

        if (extras != null) {
            // get data from previous screen
            itemView.text = "You selected ${extras.getString("itemID")}"
        }

    }
}