package com.example.recyclerview.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.models.MyModel

class MyAdapter(var ctx: Context, var arrayList: ArrayList<MyModel>): RecyclerView.Adapter<MyAdapter.ItemHolder>() {

    class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var icons = itemView.findViewById<ImageView>(R.id.icon_image)
        var text = itemView.findViewById<TextView>(R.id.textTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var myCard: MyModel = arrayList.get(position)
        holder.icons.setImageResource(myCard.iconsCard!!)
        holder.text.text = myCard.textCard

        holder.text.setOnClickListener {
            Toast.makeText(this.ctx, myCard.textCard, Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}