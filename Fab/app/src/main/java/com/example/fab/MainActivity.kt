package com.example.fab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    private lateinit var addButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton
    private lateinit var removeButton: FloatingActionButton

    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    private lateinit var listView: ListView
    private lateinit var undoOnClickListener: View.OnClickListener

    var listID: Int = 0
    var counter: Int = 0
    var canDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.add_button)
        editButton = findViewById(R.id.edit_button)
        removeButton = findViewById(R.id.image_button)

        setupList()
        setupActionButtonListeners()

        // snack bar listener
        undoOnClickListener  = View.OnClickListener {
            listItems.removeAt(listItems.size-1)
            adapter?.notifyDataSetChanged()
            Snackbar.make(it, "Item removed", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }

    private fun setupActionButtonListeners() {
        // Action Button listeners
        addButton.setOnClickListener {
            onAddButtonClicked()
            canDelete = false
        }

        editButton.setOnClickListener {
            addListItem()
            canDelete = false
            Snackbar.make(it, "Added Item ${counter}", Snackbar.LENGTH_LONG).setAction("UNDO", undoOnClickListener).show()
        }
        removeButton.setOnClickListener {
            if (listItems.size > 0) {
                com.google.android.material.snackbar.Snackbar.make(it, "Select an item to delete", com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction("", null).show()
                canDelete = true
            }
        }
    }

    private fun setupList() {
        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, i, l ->
            listID = i + 1
            if (canDelete) {
                deleteListItem(listID)
            } else {
                val itemScreen = Intent(this@MainActivity, Item_activity::class.java).apply {
                    putExtra("itemID", listItems[i])
                }
                startActivity(itemScreen)
            }
        }
    }

    // edit the list items
    private fun addListItem() {
        counter += 1
        listItems.add("Item ${counter}")
        adapter?.notifyDataSetChanged()
    }

    private fun deleteListItem(id: Int) {
        listItems.removeAt(id - 1)
        adapter?.notifyDataSetChanged()
        Toast.makeText(this, "Removed Item ${id}", Toast.LENGTH_LONG).show()
        listID = 0
        canDelete = false
    }


    // perform animation and visibility changes for ActionButtons

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            editButton.visibility = View.VISIBLE
            removeButton.visibility = View.VISIBLE
        } else {
            editButton.visibility = View.INVISIBLE
            removeButton.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            editButton.startAnimation(fromBottom)
            removeButton.startAnimation(fromBottom)
            addButton.startAnimation(rotateClose)
        } else {
            editButton.startAnimation(toBottom)
            removeButton.startAnimation(toBottom)
            addButton.startAnimation(rotateOpen)
        }
    }
}