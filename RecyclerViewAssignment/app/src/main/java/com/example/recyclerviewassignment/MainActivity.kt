package com.example.recyclerviewassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recyclerviewassignment.adatpers.MyAdapter
import com.example.recyclerviewassignment.models.MyModel

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var arrayList: ArrayList<MyModel>
    lateinit var thisAdapter: MyAdapter
    lateinit var staggered: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.list1)
        gridLayoutManager = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        staggered = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggered
//        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)

        // data
        arrayList = setupData()
        thisAdapter = MyAdapter(applicationContext, arrayList)
        recyclerView.adapter = thisAdapter

    }

    private fun setupData(): ArrayList<MyModel> {
        var items: ArrayList<MyModel> = ArrayList()

        items.add(MyModel(R.drawable.img_2558, "Dino in Utah Desert"))
        items.add(MyModel(R.drawable.img_2561, "Dino stalking prey"))
        items.add(MyModel(R.drawable.img_2564, "Dino pose"))
        items.add(MyModel(R.drawable.img_2573, "Connor posing in front of dino herd"))
        items.add(MyModel(R.drawable.img_2575, "Meat eaters talking"))
        items.add(MyModel(R.drawable.img_2571, "Watching the previous meat eaters"))
        items.add(MyModel(R.drawable.img_2577, "Another Connor pose"))
        items.add(MyModel(R.drawable.img_2578, "A raptor herd"))
        items.add(MyModel(R.drawable.img_2580, "Don't remember what this one was called"))
        items.add(MyModel(R.drawable.img_2582, "Mother watching her baby"))
        items.add(MyModel(R.drawable.img_2627, "Two Anklyosauruses"))
        items.add(MyModel(R.drawable.img_2632, "An Allosaurus stalking prey"))
        items.add(MyModel(R.drawable.img_2634, "Allosaurus roar!"))
        items.add(MyModel(R.drawable.img_2635, "Allosaurus up close"))
        items.add(MyModel(R.drawable.img_2636, "Connor running from a dinosaur"))
        items.add(MyModel(R.drawable.img_2645, "Landon playing palentologist"))
        items.add(MyModel(R.drawable.img_2647, "Connor sneaking up on dino"))
        items.add(MyModel(R.drawable.img_2651, "Connor hanging onto this raptor"))
        items.add(MyModel(R.drawable.img_2658, "Corythosaurus, probably..."))
        items.add(MyModel(R.drawable.img_2652, "A long neck"))
        items.add(MyModel(R.drawable.img_2659, "A baby triceratops?"))
        items.add(MyModel(R.drawable.img_2661, "Another group of Allosauruses?"))
        items.add(MyModel(R.drawable.img_2663, "Wait, this is Anklyosaurus!"))
        items.add(MyModel(R.drawable.img_2666, "T-Rex up close"))
        items.add(MyModel(R.drawable.img_2668, "Connor and Landon being chased by a T-Rex "))
        items.add(MyModel(R.drawable.img_2670, "Connor and Landon a T-Rex foot"))


        return items
    }
}