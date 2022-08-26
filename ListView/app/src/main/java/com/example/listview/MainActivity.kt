package com.example.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)

        val games = arrayOf(
            arrayOf("Rocket League", "Playing soccer with cars"),
            arrayOf("Minecraft", "Sandbox game where you build whatever you want."),
            arrayOf("Settlers of Catan", "Game to ruin friendships"),
            arrayOf("Dinosaur Escape", "Save three dinos from death by volcano"),
            arrayOf("Skyrim", "Greatest RPG ever made."),
            arrayOf("Rocket League", "Playing soccer with cars"),
            arrayOf("Minecraft", "Sandbox game where you build whatever you want."),
            arrayOf("Settlers of Catan", "Game to ruin friendships"),
            arrayOf("Dinosaur Escape", "Save three dinos from death by volcano"),
            arrayOf("Skyrim", "Greatest RPG ever made."),
            arrayOf("Rocket League", "Playing soccer with cars"),
            arrayOf("Minecraft", "Sandbox game where you build whatever you want."),
            arrayOf("Settlers of Catan", "Game to ruin friendships"),
            arrayOf("Dinosaur Escape", "Save three dinos from death by volcano"),
            arrayOf("Skyrim", "Greatest RPG ever made.")
        )

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1)
        for (game in games) {
            arrayAdapter.add(game[0])
        }


        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, games[i][1].toString(), Toast.LENGTH_LONG).show()
        }
    }
}