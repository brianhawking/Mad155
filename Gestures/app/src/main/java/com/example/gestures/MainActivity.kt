package com.example.gestures

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private var gDetector: GestureDetectorCompat? = null

    var buttonIDs = arrayOf(
        R.id.textView1,
        R.id.textView2,
        R.id.textView3,
        R.id.textView4,
        R.id.textView5,
        R.id.textView6,
        R.id.textView7,
        R.id.textView8,
        R.id.textView9
    )

    var colors = arrayOf(
        Color.BLUE,
        Color.YELLOW,
        Color.RED,
        Color.GREEN,
        Color.CYAN,
        Color.BLACK,
        Color.DKGRAY,
        Color.MAGENTA,
        Color.GRAY
    )

    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    lateinit var textView4: TextView
    lateinit var textView5: TextView
    lateinit var textView6: TextView
    lateinit var textView7: TextView
    lateinit var textView8: TextView
    lateinit var textView9: TextView
    lateinit var textView10: TextView

    var indexId: Int = 1
    var useUniqueColor: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.gDetector = GestureDetectorCompat(this, this)
        gDetector?.setOnDoubleTapListener(this)

        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView4 = findViewById(R.id.textView4)
        textView5 = findViewById(R.id.textView5)
        textView6 = findViewById(R.id.textView6)
        textView7 = findViewById(R.id.textView7)
        textView8 = findViewById(R.id.textView8)
        textView9 = findViewById(R.id.textView9)
        textView10 = findViewById(R.id.textView10)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            this.gDetector?.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
//        Toast.makeText(this, "onDown", Toast.LENGTH_SHORT).show()
        textView10.text = "onDown"
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        textView10.text = "onTapUp"
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
        textView10.text = "onLongPress"
        for (buttonID in buttonIDs) {
            val textview = findViewById<TextView>(buttonID)
            textview.text = "${Math.round(Math.random()*10)}"
        }
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
                Toast.makeText(this, "swipe Right", Toast.LENGTH_SHORT).show()
                moveRight()
            } else {
                Toast.makeText(this, "swipe Left", Toast.LENGTH_SHORT).show()
                moveLeft()
            }
        } else {
            if (diffY > 0) {
                Toast.makeText(this, "swipe Down", Toast.LENGTH_SHORT).show()
                moveDown()
            } else {
                Toast.makeText(this, "swipe Up", Toast.LENGTH_SHORT).show()
                moveUp()
            }
        }

        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        textView10.text = "onDoubleTap"
        useUniqueColor = !useUniqueColor
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        textView10.text = "onDoubleTapEvent"
        return true
    }

    fun moveRight() {
        remove()
        indexId += 1
        if (indexId > 9) {
            indexId = 1
        }
        add()
    }

    fun moveLeft() {
        remove()
        indexId -= 1
        if (indexId < 1) {
            indexId = 9
        }
        add()
    }

    fun moveDown() {
        remove()
        indexId += 3
        if (indexId > 9) {
            indexId -= 9
        }
        add()
    }

    fun moveUp() {
        remove()
        indexId -= 3
        if (indexId < 0) {
            indexId += 9
        }
        add()
    }

    fun remove() {
        val textView = findViewById<TextView>(buttonIDs[indexId-1])
        textView.setBackgroundColor(Color.TRANSPARENT)
        textView.setTextColor(Color.BLACK)
    }

    fun add() {
        val textView = findViewById<TextView>(buttonIDs[indexId-1])
        if (useUniqueColor) {
            textView.setBackgroundColor(colors[indexId-1])
        } else {
            textView.setBackgroundColor(Color.GREEN)
        }
        textView.setTextColor(Color.WHITE)
    }

}