package com.example.mad105project_sprint1

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GestureDetectorCompat
import com.example.mad105project_sprint1.enums.Direction
import com.example.mad105project_sprint1.math.Matrix
import com.example.mad105project_sprint1.operations.Hint
import com.example.mad105project_sprint1.operations.Operation1
import com.example.mad105project_sprint1.operations.Operation2
import com.example.mad105project_sprint1.operations.Operation3
import kotlin.math.abs

class AugmentedMatrix : AppCompatActivity(), GestureDetector.OnGestureListener {

    private var gDetector: GestureDetectorCompat? = null

    var numberOfEquations: Int = 0
    var numberOfVariables: Int = 0

    private var equationIDs = arrayOf(
        arrayOf(R.id.coefficientX1, R.id.coefficientY1,R.id.coefficientZ1, R.id.coefficientW1, R.id.coefficientC1),
        arrayOf(R.id.coefficientX2, R.id.coefficientY2, R.id.coefficientZ2, R.id.coefficientW2, R.id.coefficientC2),
        arrayOf(R.id.coefficientX3, R.id.coefficientY3, R.id.coefficientZ3, R.id.coefficientW3, R.id.coefficientC3),
        arrayOf(R.id.coefficientX4, R.id.coefficientY4, R.id.coefficientZ4, R.id.coefficientW4, R.id.coefficientC4)
    )

    // current matrix that's on screen
    private var matrix = Matrix()

    // array of matrices. Keeps track of states of the matrix
    var matrices: Array<Matrix> = emptyArray()

    private var showPivot = false
    var row = 0
    private var column = 0
    lateinit var textViewResult: TextView

    private var hintLevel = 0

    private var resultContractForHint = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult? ->
        if((result?.resultCode == Activity.RESULT_OK)){

            // get data back from activity
            val intent = result.data
            val bundle: Bundle? = intent?.extras
            val pivotElement = bundle?.getIntArray("element")
            hintLevel = bundle?.getInt("hintLevel")!!

            textViewResult = findViewById(equationIDs[row][column])
            textViewResult.setBackgroundColor(Color.TRANSPARENT)

            showPivot = true
            // proceed if data is valid
            if (pivotElement != null) {
                row = pivotElement[0]
                column = pivotElement[1]
                if (showPivot) {
                    textViewResult = findViewById(equationIDs[row][column])
                    textViewResult.setBackgroundResource(R.drawable.my_border)
                } else {
                    Toast.makeText(this, "Click SHOW PIVOT if you want to see the recommended pivot element.", Toast.LENGTH_LONG).show()
                }
            }

            hintLevel += 1

            if (hintLevel > 2) { hintLevel = 2 }

//            Toast.makeText(this, "$hintLevel", Toast.LENGTH_LONG).show()

            updateScreen()
        }
    }

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

            hintLevel = 0

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

            hintLevel = 0

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

            hintLevel = 0

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

        this.gDetector = GestureDetectorCompat(this, this)

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
            val intent = Intent(this,Hint::class.java)
            intent.putExtra("coefficients1", matrix.coefficients[0])
            intent.putExtra("coefficients2", matrix.coefficients[1])
            intent.putExtra("coefficients3", matrix.coefficients[2])
            intent.putExtra("coefficients4", matrix.coefficients[3])
            intent.putExtra("numberOfEquations", numberOfEquations)
            intent.putExtra("numberOfVariables", numberOfVariables)
            intent.putExtra("hintLevel", hintLevel)
            resultContractForHint.launch(intent)
        }

        undoButton.setOnClickListener {
            if(matrices.size > 1) {
                removeMatrixFromMatrices()
            }
        }

        pivotButton.setOnClickListener {
            when (showPivot) {
                false -> {
                    pivotButton.text = "Hide Pivot"
                    showPivot = true
                    textViewResult = findViewById(equationIDs[row][column])
                    textViewResult.setBackgroundResource(R.drawable.my_border)
                }
                true -> {
                    pivotButton.text = "Show Pivot"
                    showPivot = false
                    textViewResult = findViewById(equationIDs[row][column])
                    textViewResult.setBackgroundColor(Color.TRANSPARENT)
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

    private fun removeMatrixFromMatrices() {

        // delete last matrix from matrices
        val mutableMatrices = matrices.toMutableList()
        mutableMatrices.removeAt(matrices.size-1)
        matrices = mutableMatrices.toTypedArray()
        matrix = matrices.last()

        updateScreen()
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

    private fun moveBox(direction: Direction) : Boolean {

        val maxNumberOfEquations = equationIDs.size - 1
        val maxNumberOfVariables = equationIDs[0].size - 1

        textViewResult = findViewById(equationIDs[row][column])
        textViewResult.setBackgroundColor(Color.TRANSPARENT)


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