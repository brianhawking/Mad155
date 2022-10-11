/**
 * Class - MainActivity
 * @author - Brian Veitch
 * date - 10/20
 * @param
 * tip, tipPercentage, cost, totalCost
 * User input
 * mealCost, tip, result
 * @description
 * User enters the cost of their meal and selects a tip percentage. App will
 * calculate tip and total cost with tip.
 * mods
 * BV
 */

package com.example.mealtip

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import java.text.DecimalFormat

/**
 * Class - MainActivity
 * @author - Brian Veitch
 * @date - 10/23

 * @description
 * Calculates the tip based on the total and tip percentage

 * @param
 * cost - cost of meal
 * tipPercentage - percentage used to calculate tip
 * tip - calculated tip
 * result - dispaly tip and the overall total cost
 *
 * mods
 * BV
 */

class MainActivity : AppCompatActivity() {

    var cost: Double = 0.0
    var tipPercentage: Double = 0.0
    var tip: Double = 0.0
    var totalCost: Double = 0.0

    val PREF_NAME = "settings"
    val PREF_DARK_THEME = "dark_theme"
    val PREF_CURRENCY = "currency"
    var PREF_IMAGE = "image"

    lateinit var currencyIcon: String

    override fun onCreate(savedInstanceState: Bundle?) {

        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        var useDarkTheme = SP.getBoolean(PREF_DARK_THEME, false)
        currencyIcon = SP.getString(PREF_CURRENCY, "$").toString()
        var useImage = SP.getBoolean(PREF_IMAGE, true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (useDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // get user input
        val mealCost: EditText = findViewById(R.id.idText_MealCost)
        val tipGroup: Spinner = findViewById(R.id.IdSpinner_tip)
        val calculateButton: Button = findViewById(R.id.IdButton_calculate)
        val result: TextView = findViewById(R.id.idText_result)

        // code to run when clicking "calculate" button
        calculateButton.setOnClickListener{

            cost = mealCost.text.toString().toDouble()
            val tipString = tipGroup.selectedItem.toString()
            val currency = DecimalFormat("$currencyIcon##,##0.00")

            when (tipString) {
                "5%" -> tipPercentage = 0.05
                "10%" -> tipPercentage = 0.10
                "15%" -> tipPercentage = 0.15
                "20%" -> tipPercentage = 0.20
                "25%" -> tipPercentage = 0.25
            }

            // calculate tip and total cost
            tip = cost * tipPercentage
            totalCost = cost + tip

            // format to display
            val tipFormatted = currency.format(tip)
            val costFormatted = currency.format(totalCost)

            result.text = "Tip: $tipFormatted, Final Cost: $costFormatted"
        }
    }

    override fun onResume() {
        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        var useImage = SP.getBoolean(PREF_IMAGE, true)
        currencyIcon = SP.getString(PREF_CURRENCY, "$").toString()

        super.onResume()
        val imageView = findViewById<ImageView>(R.id.idImage_money)

        if (useImage) {
            imageView.visibility = View.VISIBLE
        } else {
            imageView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}