package com.example.mad105project_sprint1.operations

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import com.example.mad105project_sprint1.R
import com.example.mad105project_sprint1.enums.Direction
import com.example.mad105project_sprint1.math.Rational
import kotlin.math.abs

class Operation3 : AppCompatActivity(), GestureDetector.OnGestureListener  {

    private var gDetector: GestureDetectorCompat? = null

    private lateinit var rowImageView: ImageView
    private lateinit var constantView: TextView

    private var index = 0
    private val elements = arrayOf(
        R.id.initialRow,
        R.id.constant,
        R.id.pivotRow
    )

    // ====
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

    var numberOfEquations = 0
    var finalRow = 1
    var constant = "0"
    var pivotRow = 2
    var whichBox = "Initial"
    var constantSign = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = conversion(sp.getInt("theme", R.style.Theme_0))
        setTheme(theme)

        // get data from previous activity
        val bundle: Bundle? = intent.extras
        numberOfEquations = bundle?.getInt("numberOfEquations")!!

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation3)

        // set up interacted views
        rowImageView = findViewById(R.id.initialRow)
        constantView = findViewById(R.id.constant)

        // hide rows not needed
        for (i in numberOfEquations until 4) {
            val row: Button = findViewById(rowButtonIDs[i])
            row.visibility = View.GONE
        }

        this.gDetector = GestureDetectorCompat(this, this)

        setupCalculator()
        setupRowButtons()
        setupDoneButtons()
//        setupMoveButton()

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

    private fun moveBox(direction: Direction) : Boolean {
        when (direction) {
            Direction.LEFT -> {
                if (index != 0) {
                    when (index) {
                        1 -> {
                            // current box is the textview
                            constantView.setBackgroundColor(Color.TRANSPARENT)
                            index -= 1
                            rowImageView = findViewById(elements[index])
                            rowImageView.setBackgroundResource(R.drawable.my_border)
                        }
                        2 -> {
                            // current box is image but new box is image
                            rowImageView.setBackgroundColor(Color.TRANSPARENT)
                            index -= 1
                            constantView = findViewById(R.id.constant)
                            constantView.setBackgroundResource(R.drawable.my_border)
                        }
                        else -> {

                        }
                    }
                }
            }

            Direction.RIGHT -> {
                if (index != 2) {
                    when (index) {
                        0 -> {
                            // current box is image but new box is image
                            rowImageView.setBackgroundColor(Color.TRANSPARENT)
                            index += 1
                            constantView = findViewById(R.id.constant)
                            constantView.setBackgroundResource(R.drawable.my_border)

                        }
                        1 -> {
                            // current box is the textview
                            constantView.setBackgroundColor(Color.TRANSPARENT)
                            index += 1
                            rowImageView = findViewById(elements[index])
                            rowImageView.setBackgroundResource(R.drawable.my_border)
                        }
                        else -> {

                        }
                    }
                }
            }

            else -> {

            }
        }

        return true
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

//    fun setupMoveButton() {
//        val b: Button = findViewById(R.id.buttonMove)
//        b.setOnClickListener {
//
//            when(whichBox) {
//                "Initial" -> {
//                    rowImageView.setBackgroundColor(Color.TRANSPARENT)
//                    whichBox = "Constant"
//                    // highlight that box
//                    constantView = findViewById(R.id.constant)
//                    constantView.setBackgroundResource(R.drawable.my_border)
//
//                }
//                "Constant" -> {
//                    constantView.setBackgroundColor(Color.TRANSPARENT)
//                    whichBox = "Pivot"
//                    // highlight that box
//                    rowImageView = findViewById(R.id.pivotRow)
//                    rowImageView.setBackgroundResource(R.drawable.my_border)
//                }
//                "Pivot" -> {
//                    rowImageView.setBackgroundColor(Color.TRANSPARENT)
//                    whichBox = "Initial"
//                    // highlight that box
//                    rowImageView = findViewById(R.id.initialRow)
//                    rowImageView.setBackgroundResource(R.drawable.my_border)
//
//                }
//            }
//
//        }
//
//    }

    private fun editNumber(id: Int) {

        if (index != 1) {
            return
        }

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

                val view: TextView = findViewById(R.id.Plus)
                if (view.text == "+") {
                    view.text = "-"
                    constantSign = -1
                }
                else {
                    view.text = "+"
                    constantSign = 1
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
                val pivotRowImage: ImageView = findViewById(R.id.pivotRow)
                val finalRowImage: ImageView = findViewById(R.id.finalRow)

                // change row image based on user input
                when (element) {
                    R.id.row1Button -> {
                        if (index == 0) {
                            initialRowImage.setImageResource(R.drawable.r1)
                            finalRowImage.setImageResource(R.drawable.r1)
                            finalRow = 1
                        }
                        else if(index == 2) {
                            pivotRowImage.setImageResource(R.drawable.r1)
                            pivotRow = 1
                        }

                    }
                    R.id.row2Button -> {
                        if (index == 0) {
                            initialRowImage.setImageResource(R.drawable.r2)
                            finalRowImage.setImageResource(R.drawable.r2)
                            finalRow = 2
                        }
                        else if (index == 2){
                            pivotRowImage.setImageResource(R.drawable.r2)
                            pivotRow = 2
                        }
                    }
                    R.id.row3Button -> {
                        if (index == 0) {
                            initialRowImage.setImageResource(R.drawable.r3)
                            finalRowImage.setImageResource(R.drawable.r3)
                            finalRow = 3
                        }
                        else if(index == 2){
                            pivotRowImage.setImageResource(R.drawable.r3)
                            pivotRow = 3
                        }
                    }
                    R.id.row4Button -> {
                        if (index == 0) {
                            initialRowImage.setImageResource(R.drawable.r4)
                            finalRowImage.setImageResource(R.drawable.r4)
                            finalRow = 4
                        }
                        else if(index == 2){
                            pivotRowImage.setImageResource(R.drawable.r4)
                            pivotRow = 4
                        }
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

        val confirm: Button = findViewById(R.id.buttonDone)
        confirm.setOnClickListener {
            // go back with data
            prepareReturn(true)
        }
    }

    private fun prepareReturn(returnWithData: Boolean) {

        // b = true means they want to perform the operation
        if (!returnWithData) {
            // set up return with no data
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        else if (returnWithData && validate()) {
            // get data set up to send back
            val data = Intent().apply {
                putExtra("finalRow", finalRow)
                if (constantSign == -1) {
                    val temp = "-$constant"
                    constant = temp
                }
                putExtra("constant", constant)
                putExtra("pivotRow", pivotRow)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }



    }

    private fun validate() : Boolean {

        // check if final and pivot rows are the same
        if (finalRow == pivotRow) {
            Toast.makeText(
                this,
                "Your final row and pivot rows can't be the same",
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

    // GESTURES

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            this.gDetector?.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 == null || e2 == null) { return false }
        var diffX = e2.x - e1.x
        if (diffX < 0) {
            moveBox(Direction.LEFT)
        } else {
            moveBox(Direction.RIGHT)
        }

        return true
    }
}