package com.example.mad105project_sprint1.operations

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mad105project_sprint1.R
import com.example.mad105project_sprint1.math.Rational

class Operation2 : AppCompatActivity() {

    private var buttonIDs = arrayOf(
        R.id.button0,
        R.id.button1,
        R.id.button2,
        R.id.button3,
        R.id.button4,
        R.id.button5,
        R.id.button6,
        R.id.button7,
        R.id.button8,
        R.id.button9,
        R.id.buttonDEL,
        R.id.buttonPlusMinus,
        R.id.buttonFrac,
    )

    private var rowButtonIDs = arrayOf(
        R.id.row1Button,
        R.id.row2Button,
        R.id.row3Button,
        R.id.row4Button
    )

    var row = 1
    var constant = ""

    var numberOfEquations = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = conversion(sp.getInt("theme", R.style.Theme_0))
        setTheme(theme)

        val bundle: Bundle? = intent.extras
        numberOfEquations = bundle?.getInt("numberOfEquations")!!

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation2)

        // hide rows not needed
        for (i in numberOfEquations until 4) {
            val row: Button = findViewById(rowButtonIDs[i])
            row.visibility = View.GONE
        }

        setupCalculator()
        setupRowButtons()
        setupDoneButtons()
    }

    private fun setupCalculator() {

        // cycle through available buttons
        for(buttonID in buttonIDs) {

            // grab current button
            val b: Button = findViewById(buttonID)

            // set up listeners
            b.setOnClickListener {
                editNumber(buttonID)
            }
        }
    }

    private fun editNumber(id: Int) {

        // temp
        val tempResult: String

        if(constant == "0") {
            constant = ""
        }

        when (id) {
            R.id.button0 -> {
                constant += "0"
            }
            R.id.button1 -> {
                constant += "1"
            }
            R.id.button2 -> {
                constant += "2"
            }
            R.id.button3 -> {
                constant += "3"
            }
            R.id.button4 -> {
                constant += "4"
            }
            R.id.button5 -> {
                constant += "5"
            }
            R.id.button6 -> {
                constant += "6"
            }
            R.id.button7 -> {
                constant += "7"
            }
            R.id.button8 -> {
                constant += "8"
            }
            R.id.button9 -> {
                constant += "9"
            }
            R.id.buttonDEL -> {

                tempResult = constant.dropLast(1)
                constant = tempResult

                if(constant == "") {
                    constant = ""
                }
            }
            R.id.buttonPlusMinus -> {
                if(constant.contains("-", true)) {
                    // remove -
                    tempResult = constant.drop(1)
                    constant = tempResult
                }
                else {
                    val temp = constant
                    constant = "-$temp"
                }

                if(constant == "-") {
                    constant = "0"
                }
            }
            R.id.buttonFrac -> {

                // don't add a fraction if it has a / or decimal.
                if (constant.contains("/") || constant.contains(".") ) {
                    return
                }
                constant += "/"

                // don't show / if it's the only text shown
                if(constant == "/") {
                    constant = "0"
                }
            }
            else -> {
                println("Error")
            }
        }

        val textViewResult: TextView = findViewById(R.id.constant)
        textViewResult.text = constant
    }

    private fun setupRowButtons() {

        // go through the row selections
        for (element in rowButtonIDs) {

            val rowButton: Button = findViewById(element)

            // set up the listeners
            rowButton.setOnClickListener {

                val initialRowImage: ImageView = findViewById(R.id.initialRow)
                val finalRowImage: ImageView = findViewById(R.id.finalRow)

                // change row image based on user input
                when (element) {
                    R.id.row1Button -> {
                        initialRowImage.setImageResource(R.drawable.r1)
                        finalRowImage.setImageResource(R.drawable.r1)
                        row = 1
                    }
                    R.id.row2Button -> {
                        initialRowImage.setImageResource(R.drawable.r2)
                        finalRowImage.setImageResource(R.drawable.r2)
                        row = 2
                    }
                    R.id.row3Button -> {
                        initialRowImage.setImageResource(R.drawable.r3)
                        finalRowImage.setImageResource(R.drawable.r3)
                        row = 3
                    }
                    R.id.row4Button -> {
                        initialRowImage.setImageResource(R.drawable.r4)
                        finalRowImage.setImageResource(R.drawable.r4)
                        row = 3
                    }
                }
            }
        }
    }

    private fun setupDoneButtons() {
        val cancelButton: Button = findViewById(R.id.buttonCancel)
        cancelButton.setOnClickListener {
            // go back without sending any data
            prepareReturn(false)
        }

        val confirmSwap: Button = findViewById(R.id.buttonMultiply)
        confirmSwap.setOnClickListener {
            // go back with data
            prepareReturn(true)
        }
    }

    private fun prepareReturn(returnWithData: Boolean) {

        // returnWithData = true means they want to perform the operation
        if (returnWithData && validate()) {
            // get data set up to send back
            val data = Intent().apply {
                putExtra("row", row)
                putExtra("constant", constant)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
        else if(!returnWithData) {
            // set up return with no data
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun validate() : Boolean {

        if(constant == "0") {
            Toast.makeText(
                this,
                "Can't multiply by 0",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        val rational = Rational(0,1)

        println("CHECKING IF $constant is rational")
        return if(!rational.isRational(constant)) {
            Toast.makeText(
                this,
                "Your number is not valid.",
                Toast.LENGTH_LONG
            ).show()
            false
        } else {
            true
        }

    }

    private fun conversion(theme: Int): Int {
        return when (theme) {
            R.style.Theme_0 -> R.style.Theme_0_AppCompat_Translucent
            R.style.Theme_1 -> R.style.Theme_1_AppCompat_Translucent
            R.style.theme_3 -> R.style.Theme_3_AppCompat_Translucent
            R.style.theme_4 -> R.style.Theme_4_AppCompat_Translucent
            R.style.theme_5 -> R.style.Theme_5_AppCompat_Translucent
            R.style.theme_6 -> R.style.Theme_6_AppCompat_Translucent
            R.style.theme_7 -> R.style.Theme_7_AppCompat_Translucent
            else -> R.style.Theme_0_AppCompat_Translucent
        }
    }
}