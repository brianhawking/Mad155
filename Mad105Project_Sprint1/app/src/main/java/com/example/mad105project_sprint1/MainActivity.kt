package com.example.mad105project_sprint1

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import com.example.mad105project_sprint1.enums.Direction
import com.example.mad105project_sprint1.math.Matrix
import kotlin.math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private var gDetector: GestureDetectorCompat? = null

    // list of the button IDS
    private var buttonIDs = arrayOf(
        R.id.button0V2,
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
        R.id.setupMatrix,
        R.id.buttonDot,
        R.id.buttonLeft,
        R.id.buttonRight,
        R.id.buttonFrac,
        R.id.buttonSubEqn,
        R.id.buttonAddEqn,
        R.id.buttonAddVar,
        R.id.buttonSubVar
    )

    // all the coefficient textviews
    private var equationIDs = arrayOf(
        arrayOf(R.id.coefficientX1, R.id.coefficientY1,R.id.coefficientZ1,  R.id.coefficientW1, R.id.coefficientC1),
        arrayOf(R.id.coefficientX2, R.id.coefficientY2, R.id.coefficientZ2, R.id.coefficientW2, R.id.coefficientC2),
        arrayOf(R.id.coefficientX3, R.id.coefficientY3, R.id.coefficientZ3, R.id.coefficientW3, R.id.coefficientC3),
        arrayOf(R.id.coefficientX4, R.id.coefficientY4, R.id.coefficientZ4, R.id.coefficientW4, R.id.coefficientC4)
    )

    // all ids for the z variable column
    var column3IDs = arrayOf(
        R.id.equation1Z,
        R.id.equation2Z,
        R.id.equation3Z,
        R.id.equation4Z,
        R.id.equation12Plus,
        R.id.equation22Plus,
        R.id.equation32Plus,
        R.id.equation42Plus,
        R.id.coefficientZ3,
        R.id.coefficientZ2,
        R.id.coefficientZ1,
        R.id.coefficientZ4
    )

    // add ids for the w variable column
    var column4IDs = arrayOf(
        R.id.equation1W,
        R.id.equation2W,
        R.id.equation3W,
        R.id.equation4W,
        R.id.equation13Plus,
        R.id.equation23Plus,
        R.id.equation33Plus,
        R.id.equation43Plus,
        R.id.coefficientW4,
        R.id.coefficientW3,
        R.id.coefficientW2,
        R.id.coefficientW1
    )

    // all ids for equation 3
    var row3IDs = arrayOf(
        R.id.coefficientX3,
        R.id.coefficientY3,
        R.id.coefficientZ3,
        R.id.coefficientW3,
        R.id.coefficientC3,
        R.id.equation3X,
        R.id.equation31Plus,
        R.id.equation3Y,
        R.id.equation32Plus,
        R.id.equation3Z,
        R.id.equation33Plus,
        R.id.equation3W,
        R.id.equation3Equals,
    )

    // all ids for equation 3
    var row4IDs = arrayOf(
        R.id.coefficientX4,
        R.id.coefficientY4,
        R.id.coefficientZ4,
        R.id.coefficientW4,
        R.id.coefficientC4,
        R.id.equation4X,
        R.id.equation41Plus,
        R.id.equation4Y,
        R.id.equation42Plus,
        R.id.equation4Z,
        R.id.equation43Plus,
        R.id.equation4W,
        R.id.equation4Equals,
    )

    // everything
    var allElements = arrayOf(
        R.id.coefficientX3,
        R.id.coefficientY3,
        R.id.coefficientZ3,
        R.id.coefficientW3,
        R.id.coefficientC3,
        R.id.equation3X,
        R.id.equation31Plus,
        R.id.equation3Y,
        R.id.equation32Plus,
        R.id.equation3Z,
        R.id.equation33Plus,
        R.id.equation3W,
        R.id.equation3Equals,
        R.id.coefficientX4,
        R.id.coefficientY4,
        R.id.coefficientZ4,
        R.id.coefficientW4,
        R.id.coefficientC4,
        R.id.equation4X,
        R.id.equation41Plus,
        R.id.equation4Y,
        R.id.equation42Plus,
        R.id.equation4Z,
        R.id.equation43Plus,
        R.id.equation4W,
        R.id.equation4Equals,
        R.id.equation1Z,
        R.id.equation2Z,
        R.id.equation3Z,
        R.id.equation4Z,
        R.id.equation12Plus,
        R.id.equation22Plus,
        R.id.equation32Plus,
        R.id.equation42Plus,
        R.id.coefficientZ3,
        R.id.coefficientZ2,
        R.id.coefficientZ1,
        R.id.coefficientZ4,
        R.id.equation1W,
        R.id.equation2W,
        R.id.equation3W,
        R.id.equation4W,
        R.id.equation13Plus,
        R.id.equation23Plus,
        R.id.equation33Plus,
        R.id.equation43Plus,
        R.id.coefficientW4,
        R.id.coefficientW3,
        R.id.coefficientW2,
        R.id.coefficientW1
    )

    // variable containing all coefficients
    var matrix = Matrix()

    // keep track of position
    var row = 0
    var column = 0
    var numberOfEquations = 2
    var numberOfVariables = 2

    lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = sp.getInt("theme", R.style.Theme_0)
        setTheme(theme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.gDetector = GestureDetectorCompat(this, this)

        // set up 2 equations, 2 variables
        setup(2,2)

        // current selected box
        textViewResult = findViewById(equationIDs[row][column])

        // add listeners to all the buttons
        for(buttonID in buttonIDs) {
            val b: Button = findViewById(buttonID)

            b.setOnClickListener {

                // user selects to move
                if( (buttonID == R.id.buttonLeft) || (buttonID == R.id.buttonRight)) {
//                    moveBox(buttonID)
                }
                // user wants to change size of system
                else if(buttonID == R.id.buttonSubVar || buttonID == R.id.buttonAddVar || buttonID == R.id.buttonAddEqn || buttonID == R.id.buttonSubEqn) {
                    changeSize(buttonID)
                }
                else if(buttonID == R.id.setupMatrix) {
                    if (validate()) {
                        // move to next screen
                        setupAugmentedMatrix()
                    }
                }
                else {
                    editNumber(buttonID)
                }
            }
        }

    }

    private fun hideColumn(column: Int) {
        if (column == 4) {
            for (element in column4IDs) {
                val box: TextView = findViewById(element)
                box.visibility = View.GONE
            }
        }
        if (column == 3) {
            for (element in column3IDs) {
                val box: TextView = findViewById(element)
                box.visibility = View.GONE
            }
        }
    }

    private fun hideRow(row: Int) {
        if (row == 4) {
            for(element in row4IDs) {
                val box: TextView = findViewById(element)
                box.visibility = View.GONE
            }
        }
        if (row == 3) {
            for(element in row3IDs) {
                val box: TextView = findViewById(element)
                box.visibility = View.GONE
            }
        }
    }

    private fun setup(equations: Int, variables: Int) {

        // show everything
        for (element in allElements) {
            val box: TextView = findViewById(element)
            box.visibility = View.VISIBLE
        }

        // take away the rows and columns not needed
        if (variables == 2) {
            hideColumn(3)
            hideColumn(4)
        } else if (variables == 3) {
            hideColumn(4)
        }

        if (equations == 2) {
            hideRow(3)
            hideRow(4)
        } else if (equations == 3) {
            hideRow(4)
        }
    }

    private fun changeSize(id: Int) {

        // which size adjustment did they make
        when (id) {
            R.id.buttonSubVar -> {
                numberOfVariables -= 1
                if (numberOfVariables < 2) { numberOfVariables = 2 }
                setup(numberOfEquations, numberOfVariables)
            }
            R.id.buttonAddVar -> {
                numberOfVariables += 1
                if (numberOfVariables > 4) { numberOfVariables = 4 }
                setup(numberOfEquations, numberOfVariables)
            }
            R.id.buttonAddEqn -> {
                numberOfEquations += 1
                if (numberOfEquations > 4) { numberOfEquations = 4 }
                setup(numberOfEquations, numberOfVariables)
            }
            R.id.buttonSubEqn -> {
                numberOfEquations -= 1
                if (numberOfEquations < 2) { numberOfEquations = 2 }
                setup(numberOfEquations, numberOfVariables)
            }
        }
    }

    private fun setupAugmentedMatrix() {
        // set up transition to next activity
        val intent = Intent(this,AugmentedMatrix::class.java)

        // set data up to send to next activity
        intent.putExtra("coefficients1", matrix.coefficients[0])
        intent.putExtra("coefficients2", matrix.coefficients[1])
        intent.putExtra("coefficients3", matrix.coefficients[2])
        intent.putExtra("coefficients4", matrix.coefficients[3])
        intent.putExtra("numberOfEquations", numberOfEquations)
        intent.putExtra("numberOfVariables", numberOfVariables)

        startActivity(intent)
    }

    // make sure the number is valid
    private fun validate() : Boolean {
        // search through coefficients. make sure they're all valid numbers
        // if they're all numbers, convert to Rational
        for(i in matrix.coefficients.indices) {
            for (j in matrix.coefficients[i].indices) {

                // if the string to rational returns false, return false for validate
                if (!matrix.stringToRational(i,j)) {
                    Toast.makeText(
                        this,
                        "Your coefficients is invalid.",
                        Toast.LENGTH_LONG
                    ).show()
                    return false
                }
                updateBox(i,j)
            }
        }
        return true
    }

    private fun updateBox(row: Int, column: Int) {
        val box: TextView = findViewById(equationIDs[row][column])
        box.text = matrix.coefficientsAsRationals[row][column].toString()
    }

    private fun moveBox(direction: Direction) : Boolean {

        val maxNumberOfEquations = equationIDs.size - 1
        val maxNumberOfVariables = equationIDs[0].size - 1

        if(!validate()) {
            println("Your number is invalid.")
            return false
        }
        else {
            textViewResult = findViewById(equationIDs[row][column])
            textViewResult.setBackgroundColor(Color.TRANSPARENT)
        }

        when (direction) {

            Direction.UP
            -> {
                // move up
                row -= 1
                if (row < 0) {
                    row = numberOfEquations - 1
                }
            }

            Direction.DOWN -> {
                // move down
                row += 1
                if (row == numberOfEquations) {
                    row = 0
                }
            }

            Direction.LEFT -> {
                // move left
                column -= 1

                if(column < 0) {
                    column = maxNumberOfVariables
                }
                if (column == maxNumberOfVariables - 1) {
                    column = numberOfVariables - 1
                }
            }
            Direction.RIGHT -> {
                // move right
                column += 1

                if (column == numberOfVariables) {
                    column = maxNumberOfVariables
                }

                if (column > maxNumberOfVariables) {
                    column = 0
                    row += 1
                    if (row == numberOfEquations) {
                        row = 0
                    }
                }
            }
            else -> {
                println("ERROR")
            }
        }

        textViewResult = findViewById(equationIDs[row][column])
        textViewResult.setBackgroundResource(R.drawable.my_border)

        return true
    }

    private fun editNumber(id: Int) {


        if(matrix.coefficients[row][column] == "0") {
            matrix.coefficients[row][column] = ""
        }

        val tempResult: String

        when (id) {
            R.id.button0V2 -> {
                // don't add a 0 if the number is already 0
                if(matrix.coefficients[row][column] == "0") {
                    return
                }
                // don't add a zero if the decimal already ends in a 0
                if( (matrix.coefficients[row][column].contains(".", false)) && (matrix.coefficients[row][column].last() == '0')) {
                    return
                }
                matrix.coefficients[row][column] += "0"
            }
            R.id.button1 -> {
                matrix.coefficients[row][column] += "1"
            }
            R.id.button2 -> {
                matrix.coefficients[row][column] += "2"
            }
            R.id.button3 -> {
                matrix.coefficients[row][column] += "3"
            }
            R.id.button4 -> {
                matrix.coefficients[row][column] += "4"
            }
            R.id.button5 -> {
                matrix.coefficients[row][column] += "5"
            }
            R.id.button6 -> {
                matrix.coefficients[row][column] += "6"
            }
            R.id.button7 -> {
                matrix.coefficients[row][column] += "7"
            }
            R.id.button8 -> {
                matrix.coefficients[row][column] += "8"
            }
            R.id.button9 -> {
                matrix.coefficients[row][column] += "9"
            }
            R.id.buttonDEL -> {

                tempResult = matrix.coefficients[row][column].dropLast(1)
                matrix.coefficients[row][column] = tempResult

                if(matrix.coefficients[row][column] == "") {
                    matrix.coefficients[row][column] = "0"
                }
            }
            R.id.buttonPlusMinus -> {
                if(matrix.coefficients[row][column].contains("-", true)) {
                    // remove -
                    tempResult = matrix.coefficients[row][column].drop(1)
                    matrix.coefficients[row][column] = tempResult
                }
                else {
                    val temp = matrix.coefficients[row][column]
                    matrix.coefficients[row][column] = "-$temp"
                }

                if(matrix.coefficients[row][column] == "-") {
                    matrix.coefficients[row][column] = "0"
                }
            }
            R.id.buttonDot -> {

                if(matrix.coefficients[row][column].contains(".")) {
                    return
                }

                if(matrix.coefficients[row][column].contains("/")){
                    return
                }

                matrix.coefficients[row][column] += "."

                if(matrix.coefficients[row][column] == ".") {
                    matrix.coefficients[row][column] = "0."
                }
            }
            R.id.buttonFrac -> {

                // don't add a fraction if it has a / or decimal.
                if (matrix.coefficients[row][column].contains("/") || matrix.coefficients[row][column].contains(".") ) {
                    return
                }
                matrix.coefficients[row][column] += "/"

                // don't show / if it's the only text shown
                if(matrix.coefficients[row][column] == "/") {
                    matrix.coefficients[row][column] = "0"
                }
            }
            else -> {
                println("Error")
            }
        }

        //println(coefficients[row][column])
        textViewResult = findViewById(equationIDs[row][column])
        textViewResult.text = matrix.coefficients[row][column]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.settings -> {
            val intent = Intent(this,Settings::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
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
        var diffY = e2.y - e1.y
        var diffX = e2.x - e1.x

        if (abs(diffX) > abs(diffY)) {
            if (diffX > 0) {
                moveBox(Direction.RIGHT)
            } else {
                moveBox(Direction.LEFT)
            }
        } else {
            if (diffY > 0) {
                moveBox(Direction.DOWN)
            } else {
                moveBox(Direction.UP)
            }
        }

        return true
    }
}