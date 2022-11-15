package com.example.mad105project_sprint1.operations

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import com.example.mad105project_sprint1.R
import com.example.mad105project_sprint1.enums.Direction
import kotlin.math.abs

class Operation3 : AppCompatActivity(), GestureDetector.OnGestureListener  {

    private var gDetector: GestureDetectorCompat? = null

    lateinit var rowImageView: ImageView
    lateinit var constantView: TextView

    private var index = 0
    private val elements = arrayOf(
        R.id.initialRow,
        R.id.constant,
        R.id.pivotRow
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        val sp: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        val theme = conversion(sp.getInt("theme", R.style.Theme_0))
        setTheme(theme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation3)

        // set up interacted views
        rowImageView = findViewById(R.id.initialRow)
        constantView = findViewById(R.id.constant)

        this.gDetector = GestureDetectorCompat(this, this)


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