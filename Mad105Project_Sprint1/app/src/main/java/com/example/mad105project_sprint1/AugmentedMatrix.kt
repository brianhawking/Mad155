package com.example.mad105project_sprint1

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mad105project_sprint1.math.Matrix
import com.example.mad105project_sprint1.operations.Hint
import com.example.mad105project_sprint1.operations.Operation1
import com.example.mad105project_sprint1.operations.Operation2
import com.example.mad105project_sprint1.operations.Operation3

class AugmentedMatrix : AppCompatActivity() {

    lateinit var operation1Button: ImageButton
    lateinit var operation2Button: ImageButton
    lateinit var operation3Button: ImageButton

    var numberOfEquations: Int = 0
    var numberOfVariables: Int = 0

    private var equationIDs = arrayOf(
        arrayOf(R.id.coefficientX1, R.id.coefficientY1,R.id.coefficientZ1, R.id.coefficientW1, R.id.coefficientC1),
        arrayOf(R.id.coefficientX2, R.id.coefficientY2, R.id.coefficientZ2, R.id.coefficientW2, R.id.coefficientC2),
        arrayOf(R.id.coefficientX3, R.id.coefficientY3, R.id.coefficientZ3, R.id.coefficientW3, R.id.coefficientC3),
        arrayOf(R.id.coefficientX4, R.id.coefficientY4, R.id.coefficientZ4, R.id.coefficientW4, R.id.coefficientC4)
    )

    // current matrix that's on screen
    var matrix = Matrix()

    // array of matrices. Keeps track of states of the matrix
    var matrices: Array<Matrix> = emptyArray()

    var showPivot = true
    var row = 0
    var column = 0

    private var resultContractForSwap = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult? ->
        if((result?.resultCode == Activity.RESULT_OK)){

            // get data back from activity
            val intent = result.data
            val bundle: Bundle? = intent?.extras
            val rowsToSwap = bundle?.getIntArray("rows")

            // proceed if data is valid
            if (rowsToSwap != null) {

                /* make copy of matrix
                swap rows
                add new matrix to matrices array
                copy new matrix back
                 */
                val tempMatrix = matrix.copy()
                tempMatrix.swapRows(rowsToSwap[0],rowsToSwap[1])
                addMatrixToMatrices(tempMatrix)
                matrix = tempMatrix.copy()
            }

            // update screen with new matrix
            updateScreen()
        }
    }

    private var resultContractForConstant = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult? ->
        if((result?.resultCode == Activity.RESULT_OK)){

            // get data back from activity
            val intent = result.data
            val bundle: Bundle? = intent?.extras
            val constant = bundle?.getString("constant")
            val row = bundle?.getInt("row")

            // proceed if data is valid
            if (row != null) {
                if (constant != null) {

                    /* make copy of matrix
                    multiply row by constant
                    add new matrix to matrices array
                    copy new matrix back
                     */
                    val tempMatrix = matrix.copy()
                    tempMatrix.multiplyRowByConstant(row,constant)
                    addMatrixToMatrices(tempMatrix)
                    matrix = tempMatrix.copy()
                }
            }

            // update screen with new matrix
            updateScreen()
        }
    }

    private var resultContractRowPlusConstantRow = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult? ->
        if((result?.resultCode == Activity.RESULT_OK)){

            // get data back from activity
            val intent = result.data
            val bundle: Bundle? = intent?.extras
            val constant = bundle?.getString("constant")
            val finalRow = bundle?.getInt("finalRow")
            val pivotRow = bundle?.getInt("pivotRow")

            // proceed if data is valid
            if (pivotRow != null && finalRow != null && constant != null) {

                /* make copy of matrix
                perform operation
                add new matrix to matrices array
                copy new matrix back
                 */
                val tempMatrix = matrix.copy()
                tempMatrix.rowPlusConstantRow(finalRow, constant, pivotRow)
                addMatrixToMatrices(tempMatrix)
                matrix = tempMatrix.copy()

            }

            // update screen with new matrix
            updateScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = sp.getInt("theme", R.style.Theme_0)
        setTheme(theme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augmented_matrix)

        // get coefficients from previous activity
        val bundle: Bundle? = intent.extras
        val coefficients1 = bundle?.getStringArray("coefficients1")
        val coefficients2 = bundle?.getStringArray("coefficients2")
        val coefficients3 = bundle?.getStringArray("coefficients3")
        val coefficients4 = bundle?.getStringArray("coefficients4")
        numberOfEquations = bundle?.getInt("numberOfEquations")!!
        numberOfVariables = bundle.getInt("numberOfVariables")

        // get data from bundle. Store it in the matrix as string and rational
        for (i in matrix.coefficients.indices) {
            for (j in matrix.coefficients[i].indices) {
                when (i) {
                    0 -> matrix.coefficients[i][j] = coefficients1?.get(j).toString()
                    1 -> matrix.coefficients[i][j] = coefficients2?.get(j).toString()
                    2 -> matrix.coefficients[i][j] = coefficients3?.get(j).toString()
                    3 -> matrix.coefficients[i][j] = coefficients4?.get(j).toString()
                    else -> println("SOMETHING BAD HAPPENED")
                }
                // create the Rational version of the number
                matrix.stringToRational(i,j)
            }
        }

        // display data to the matrix on screen
        addMatrixToMatrices(matrix.copy())
        printMatrix(matrix.copy())
        updateScreen()

        // set up button listeners for the operations
        val operation1Button: ImageButton = findViewById(R.id.operation1Button)
        val operation2Button: ImageButton = findViewById(R.id.operation2Button)
        val operation3Button: ImageButton = findViewById(R.id.operation3Button)
        val undoButton: Button = findViewById(R.id.undoButton)
        val hintButton: Button = findViewById(R.id.hintButton)
        val pivotButton: Button = findViewById(R.id.pivotHintButton)


        operation1Button.setOnClickListener {
            val intent = Intent(this,Operation1::class.java)
            intent.putExtra("numberOfEquations",numberOfEquations)
            resultContractForSwap.launch(intent)
        }

        operation2Button.setOnClickListener {
            val intent = Intent(this, Operation2::class.java)
            intent.putExtra("numberOfEquations",numberOfEquations)
            resultContractForConstant.launch(intent)
        }

        operation3Button.setOnClickListener {
            val intent = Intent(this, Operation3::class.java)
            intent.putExtra("numberOfEquations", numberOfEquations)
            resultContractRowPlusConstantRow.launch(intent)
        }

        hintButton.setOnClickListener {
            val intent = Intent(this, Hint::class.java)
            startActivity(intent)
        }

        pivotButton.setOnClickListener {
            Toast.makeText(this, "Pivot pressed", Toast.LENGTH_LONG).show()
            when (showPivot) {
                false -> {
                    pivotButton.text = "Hide Pivot"
                    showPivot = true
                }
                true -> {
                    pivotButton.text = "Show Pivot"
                    showPivot = false
                }
            }
        }
    }

    // add the new matrix from row operation to the matrices array
    private fun addMatrixToMatrices(tempMatrix: Matrix) {
        // convert to mutable list so I can add a new element to array
        val mutableMatrices = matrices.toMutableList()
        mutableMatrices.add(tempMatrix)
        matrices = mutableMatrices.toTypedArray()
    }

    // print matrix to console for debugging
    private fun printMatrix(tempMatrix: Matrix) {
        for(row in tempMatrix.coefficients.indices) {
            for(column in tempMatrix.coefficientsAsRationals[row].indices) {
                print("${tempMatrix.coefficientsAsRationals[row][column]}")
            }
            print("\n")
        }
    }


    private fun updateScreen() {

        // grab all the textviews, update with new matrix value
        for(i in equationIDs.indices) {
            for(j in equationIDs[i].indices) {
                val box: TextView = findViewById(equationIDs[i][j])
                box.text = matrix.coefficientsAsRationals[i][j].toString()
            }
        }

        // hide equations if needed
        if(numberOfEquations == 2) {
            hideRow(3)
            hideRow(4)
        } else if (numberOfEquations == 3) {
            hideRow(4)
        }

        // hide columns, if needed
        if(numberOfVariables == 2) {
            hideColumn(3)
            hideColumn(4)
        } else if (numberOfVariables == 3) {
            hideColumn(4)
        }
    }

    private fun hideRow(row: Int) {
        if (row == 3) {
            val row3: TableRow = findViewById(R.id.row3)
            row3.visibility = View.GONE
            val row3Divider = findViewById<View>(R.id.row3Divider)
            row3Divider.visibility = View.GONE
        }
        else if (row == 4) {
            val row4: TableRow = findViewById(R.id.row4)
            row4.visibility = View.GONE
            val row4Divider: View = findViewById(R.id.row4Divider)
            row4Divider.visibility = View.GONE
        }
    }

    private fun hideColumn(column: Int) {
        if (column == 3) {
            // hide column 3
            for(i in equationIDs.indices) {
                val row: TextView = findViewById(equationIDs[i][2])
                row.visibility = View.GONE
            }
        }
        else if (column == 4) {
            // hide column 3
            for(i in equationIDs.indices) {
                val row: TextView = findViewById(equationIDs[i][3])
                row.visibility = View.GONE
            }
        }
    }


}