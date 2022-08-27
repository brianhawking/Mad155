package com.example.customlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(
    var ctx: Context,
    var ourResource: Int,
    var Items: ArrayList<ProfileModel>
    ): ArrayAdapter<ProfileModel>(ctx, ourResource, Items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(ourResource, null)

        // goto layout and get links to our items
        val name = view.findViewById<TextView>(R.id.date)
        val extra = view.findViewById<TextView>(R.id.snippet)
        val image = view.findViewById<ImageView>(R.id.image1)

        name.text = Items[position].name
        extra.text = Items[position].extraText
        image.setImageDrawable(ctx.resources.getDrawable(Items[position].image))

        return view
    }

    }