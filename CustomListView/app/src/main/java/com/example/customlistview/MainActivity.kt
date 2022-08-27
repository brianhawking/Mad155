package com.example.customlistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)

        val list = ArrayList<ProfileModel>()

        list.add(ProfileModel("August 27, 2022", "Dear Santa, I want a bike please.", R.drawable.lettertosanta))
        list.add(ProfileModel("August 11, 2022", "Dear Bubbles, I want to tell you about my first day of school", R.drawable.sleigh))
        list.add(ProfileModel("July 18, 2022", "What made me smile today? I went to the dino museum in ...", R.drawable.print3))
        list.add(ProfileModel("June 22, 2022", "Dear Diary, I broke my leg today at my martial arts picnic ...", R.drawable.diary1))
        list.add(ProfileModel("August 27, 2022", "Dear Santa, I want a bike please.", R.drawable.lettertosanta))
        list.add(ProfileModel("August 11, 2022", "Dear Bubbles, I want to tell you about my first day of school", R.drawable.sleigh))
        list.add(ProfileModel("July 18, 2022", "What made me smile today? I went to the dino museum in ...", R.drawable.print3))
        list.add(ProfileModel("June 22, 2022", "Dear Diary, I broke my leg today at my martial arts picnic ...", R.drawable.diary1))

        listView.adapter = CustomAdapter(this, R.layout.custom_item_layout, list)

    }
}