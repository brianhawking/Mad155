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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get user input
        val mealCost: EditText = findViewById(R.id.idText_MealCost)
        val tipGroup: Spinner = findViewById(R.id.IdSpinner_tip)
        val calculateButton: Button = findViewById(R.id.IdButton_calculate)
        val result: TextView = findViewById(R.id.idText_result)

        // code to run when clicking "calculate" button
        calculateButton.setOnClickListener{

            cost = mealCost.text.toString().toDouble()
            val tipString = tipGroup.selectedItem.toString()
            val currency = DecimalFormat("$##,###.00")

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
}