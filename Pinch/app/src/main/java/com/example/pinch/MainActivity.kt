package com.example.pinch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView

lateinit var imageView: ImageView
var scale: Float = 1f


class MainActivity : AppCompatActivity() {

    var ourScaleDetector: ScaleGestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.dinoImage)
        ourScaleDetector = ScaleGestureDetector(this, ScaleListener())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        ourScaleDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    class ScaleListener: ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {

            scale *= detector?.scaleFactor!!
            scale = Math.max(0.1f, Math.min(scale, 5.0f))
            imageView.scaleX = scale
            imageView.scaleY = scale

            return true
        }
    }
}