package com.example.mad105project_sprint1.operations

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mad105project_sprint1.R
import com.example.mad105project_sprint1.math.Matrix
import com.example.mad105project_sprint1.math.Rational

class Hint : AppCompatActivity() {

    var matrix = Matrix()
    var numberOfEquations = 4
    var numberOfVariables = 4
    var pivotRow = 0
    var pivotColumn = 0

    private val cZERO = Rational(0,1)
    private val cONE = Rational(1,1)

    private var hintViews = arrayOf(
        R.id.swapRowsHint,
        R.id.multiplyByConstantHint,
        R.id.rowPlusConstantRowHint
    )

    private var rowImages = arrayOf(
        R.drawable.r12,
        R.drawable.r22,
        R.drawable.r32,
        R.drawable.r42
    )

//    var rows = arrayOf(
//        arrayOf(R.id.coefficientX1, R.id.coefficientY1, R.id.coefficientZ1, R.id.coefficientC1),
//        arrayOf(R.id.coefficientX2, R.id.coefficientY2, R.id.coefficientZ2, R.id.coefficientC2),
//        arrayOf(R.id.coefficientX3, R.id.coefficientY3, R.id.coefficientZ3, R.id.coefficientC3)
//    )

    private var solutions = arrayOf(
        arrayOf("x", ""),
        arrayOf("y", ""),
        arrayOf("z", ""),
        arrayOf("w", "")
    )

    var hintLevel = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = conversion(sp.getInt("theme", R.style.Theme_0))
        setTheme(theme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hint)

        // get coefficients from previous activity
        val bundle: Bundle? = intent.extras
        val coefficients1 = bundle?.getStringArray("coefficients1")
        val coefficients2 = bundle?.getStringArray("coefficients2")
        val coefficients3 = bundle?.getStringArray("coefficients3")
        val coefficients4 = bundle?.getStringArray("coefficients4")
        numberOfEquations = bundle?.getInt("numberOfEquations")!!
        numberOfVariables = bundle.getInt("numberOfVariables")
        hintLevel = bundle?.getInt("hintLevel")!!

        // get data from bundle. Store it in the matrix as string and rational
        for (i in matrix.coefficients.indices) {
            for (j in matrix.coefficients[i].indices) {
                when (i) {
                    0 ->  matrix.coefficients[i][j] = coefficients1?.get(j).toString()
                    1 ->  matrix.coefficients[i][j] = coefficients2?.get(j).toString()
                    2 ->  matrix.coefficients[i][j] = coefficients3?.get(j).toString()
                    3 ->  matrix.coefficients[i][j] = coefficients4?.get(j).toString()
                    else -> println("SOMETHING BAD HAPPENED")
                }
                // create the Rational version of the number
                matrix.stringToRational(i,j)
            }
        }

        val doneButton: Button = findViewById(R.id.doneButton)
        doneButton.setOnClickListener {
            val pivot = intArrayOf(pivotRow,pivotColumn)
            val data = Intent().apply {
                putExtra("element", pivot)
                putExtra("hintLevel", hintLevel)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        for (linearlayoutID in hintViews) {
            val layout: LinearLayout = findViewById(linearlayoutID)
            layout.visibility = View.GONE
        }

        findPivot()

    }

    private fun findPivot() {

        //println("Row $pivotRow, Column $pivotColumn")
        if(pivotColumn >= numberOfVariables) {
            readTable()
        }

        // count how many 0s, 1s, others are in the column
        val ones = arrayOf(false, false, false, false)
        val zeros = arrayOf(false, false, false, false)
        val nonzeros = arrayOf(false, false, false, false)


        // scan through rows
        for (row in 0 until numberOfEquations) {

            // number in column
            val element = matrix.coefficientsAsRationals[row][pivotColumn]

            when {
                element.equals(cONE) -> {
                    ones[row] = true
                }
                element.equals(cZERO) -> {
                    zeros[row] = true
                }
                else -> {
                    nonzeros[row] = true
                }
            }
        }

        // is the column made up of all 0s
        if (zeros.count {it} == (numberOfEquations)) {
            println("COLUMN does not have a pivot. Move to next column")
            pivotColumn += 1

            if(pivotColumn == numberOfVariables) {
                readTable()
            }
            else {
                findPivot()
            }
        }
        // you have a unit column
        else if (ones.count {it} >= 1){
            // this is a unit column
            if (ones[pivotRow] && zeros.count{it} == (numberOfEquations-1)) {

                pivotRow += 1
                pivotColumn += 1
                if(pivotColumn == numberOfVariables) {
                    readTable()
                }
                else {
                    findPivot()
                }

            }
            else if (ones[pivotRow]) {
                operation3Step(nonzeros.indexOf(true))
            }
            else if (ones.indexOf(true) >= pivotRow){
                operation1Step(ones.indexOf(true))
            } else {
                pivotColumn += 1
                if(pivotColumn == numberOfVariables) {
                    readTable()
                }
                else {
                    findPivot()
                }
            }
        }
        else {
            // a non zero exists
            val element = matrix.coefficientsAsRationals[pivotRow][pivotColumn]
            if (element.equals(cZERO)) {
                // swap with another row
                var counter = 0
                for (row in nonzeros.indices) {
                    if (row == numberOfEquations) {
                        break
                    }
                    if (nonzeros[row] && row > pivotRow) {
                        operation1Step(row)
                        break
                    }
                    counter += 1
                }

                if(counter == numberOfEquations) {
                    pivotColumn += 1
                    if (pivotColumn == numberOfVariables) {
                        readTable()
                    } else {
                        findPivot()
                    }
                }
            }
            else {
                // turn into a 1
                operation2Step(pivotRow)
            }
        }
    }

    private fun showHint(operation: Int, row: Int) {
        val hintText: TextView = findViewById(R.id.hintText)
        if (hintLevel == 0) {
            hintText.text =
                "The pivot element is \n\nRow ${pivotRow+1}, Column ${pivotColumn+1}. \n\nIt will be highlighted when you go back."
            return
        }

        if (hintLevel == 1) {
            val number = matrix.coefficientsAsRationals[pivotRow][pivotColumn]
            println(number.toString())
            // tell them which operation
            when (operation) {
                1 -> {
                    if (number.equals(cZERO)) {
                        hintText.text =
                            "The pivot element is a '0'. You need it to be a '1'. Since you can't multiply '0' by anything to get '1', try swapping rows to move the '0'."
                    } else {
                        hintText.text = "You want the pivot element to be a '1'. Looks like you can swap rows to make this happen right away."
                    }
                }
                2 -> hintText.text = "You want the pivot element to be a '1'. Try multiplying row ${row+1} by the reciprocal of the pivot element."
                3 -> hintText.text = "Your pivot element is a '1', but the rest of the column needs to be a 0. Use operation $operation to do this."
            }
        } else {
            hintText.text = "Use this..."
            val layout: LinearLayout = findViewById(hintViews[operation-1])
            layout.visibility = View.VISIBLE
        }
    }

    private fun operation1Step(row: Int) {
        showHint(1, row)

        val rowI: ImageView = findViewById(R.id.operation1RowI)
        val rowJ: ImageView = findViewById(R.id.operation1RowJ)

        rowI.setImageResource(rowImages[pivotRow])
        rowJ.setImageResource(rowImages[row])
        println("Swap Row ${pivotRow+1} with Row ${row+1}")
    }

    private fun operation2Step(row: Int) {
        showHint(2, row)
        val operation2Constant: TextView = findViewById(R.id.operation2Constant)
        val operation2InitialRow: ImageView = findViewById(R.id.operation2InitialRow)
        val operation2FinalRow: ImageView = findViewById(R.id.operation2FinalRow)

        val number = matrix.coefficientsAsRationals[row][pivotColumn]
        operation2Constant.text = number.reciprocal().toString()
        operation2InitialRow.setImageResource(rowImages[pivotRow])
        operation2FinalRow.setImageResource(rowImages[pivotRow])


        println("Multiply Row ${row+1} by ${number.reciprocal()}")
    }

    private fun operation3Step(row: Int) {
        showHint(3, row)

        val operation3Constant: TextView = findViewById(R.id.operation3Constant)
        val operation3InitialRow: ImageView = findViewById(R.id.operation3initialRow)
        val operation3PivotRow: ImageView = findViewById(R.id.operation3PivotRow)
        val operation3FinalRow: ImageView = findViewById(R.id.operation3FinalRow)

        val number = matrix.coefficientsAsRationals[row][pivotColumn]
        operation3Constant.text = number.negate().toString()
        operation3InitialRow.setImageResource(rowImages[row])
        operation3FinalRow.setImageResource(rowImages[row])
        operation3PivotRow.setImageResource(rowImages[pivotRow])
        println("Row ${row+1} + ${number.negate()} * Row ${pivotRow+1}")
    }

    /* Description:
    When the matrix is row reduced, readTable() will interpret the result
     */
    private fun readTable() {
        println("READ TABLE")
        val hintText: TextView = findViewById(R.id.hintText)
        hintText.text = ""
        var solution = "Solution:"

        // check for no solution
        if(noSolution()) {
            return
        }

        // either a unique or infinite solution
        var row = 0
        for (i in 0 until numberOfEquations) {

            if(i <= numberOfVariables-1) {

                if (!(matrix.coefficientsAsRationals[row][i].equals(cONE))) {
                    // variable is infinite

                    solutions[row][1] = "anything"
                    solution += "\n${solutions[row][0]} = anything"
                    row += 1

                    if (row == numberOfVariables) {
                        break
                    }

                    if (!(matrix.coefficientsAsRationals[i][i+1].equals(cONE))) {
                        solutions[row][1] = "anything"
                        solution += "\n${solutions[row][0]} = anything"
                        row += 1
                    }

                    if (row == numberOfVariables) {
                        break
                    }
                }

//                if (!(matrix.coefficientsAsRationals[i][4].equals(cZERO))) {
                    solutions[row][1] = matrix.coefficientsAsRationals[i][4].toString()
//                }
                println("number of variables $numberOfVariables")
                for (j in row+1 until numberOfVariables) {

                    if (!matrix.coefficientsAsRationals[i][j].equals(cZERO)) {

                        solutions[row][1] += " +${matrix.coefficientsAsRationals[i][j].negate()}${solutions[j][0]}"
                        solutions[row][1] = solutions[row][1].replace("+-", "- ", true)
                    }

                    if(solutions[row][1][0] == '+') {
                        solutions[row][1].drop(1)
                    }
                }

                solution += "\n${solutions[row][0]} = ${solutions[row][1]}"
                row += 1

                if (row == numberOfVariables) {
                    break
                }

            }

        }

        hintText.text = solution
    }

    private fun noSolution() : Boolean {
        for (i in 0 until numberOfEquations) {
            var numberOfZeros = 0
            for (j in 0 until numberOfVariables) {
                if (matrix.coefficientsAsRationals[i][j].equals(cZERO)) {
                    numberOfZeros += 1
                }
            }
            println("number of zeros $numberOfZeros, $numberOfVariables")
            if (numberOfZeros == numberOfVariables && !(matrix.coefficientsAsRationals[i][4].equals(cZERO)) ) {
                // no solution
                var v = "Because \n0x + 0y"
                if(numberOfVariables == 3) {
                    v += " + 0z"
                }

                v += " = ${matrix.coefficientsAsRationals[i][4]}"
                val hintTextView: TextView = findViewById(R.id.hintText)

                v += "\nhas no solution, the system of equations has no solution"

                hintTextView.text = v
                println("YOU DIDNt have a solution")
                return true
            }
        }
        println("YOu have a solution")
        return false
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