package com.example.topactionbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    var favorite: Boolean = false
    lateinit var favoriteImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Note that the Toolbar defined in the layout has the id "my_toolbar"
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnSearchClickListener {
            Toast.makeText(this, "HI", Toast.LENGTH_LONG).show()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                println("1 - $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.

                println(newText)
                return false
            }
        })

        // Configure the search info and add any event listeners..

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_menu -> {
            Toast.makeText(this, "You touched the menu", Toast.LENGTH_LONG).show()
            true
        }

        R.id.action_favorite -> {
            if (favorite) {
                item.setIcon(R.drawable.ic_baseline_favorite_border_24)
            } else {
                item.setIcon(R.drawable.ic_baseline_favorite_24)
            }
            favorite = !favorite
            println(favorite)
            true
        }

        R.id.item1 -> {
            val intent = Intent(this, Item1::class.java).apply {
//                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
            true
        }

        R.id.item2 -> {
            val intent = Intent(this, Item1::class.java).apply {
//                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}