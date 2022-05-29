package dev.eastar.gravity

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.window.layout.WindowMetricsCalculator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContentView(FrameLayout(context).apply {
//            setBackgroundColor(0x55ff0000)
            addView(GravityView(context).apply {
                setBackgroundColor(0x55ff0000)
            })
        })
    }
}

class GravityView(context: Context) : View(context) {
    private lateinit var currentWindowMetricsBounds: Rect
    private var h: Int = 0
    private var w: Int = 0
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        obtainWindowMetrics()
    }

    private fun obtainWindowMetrics() {
        val wmc = WindowMetricsCalculator.getOrCreate()
        currentWindowMetricsBounds = wmc.computeCurrentWindowMetrics(context as Activity).bounds
        Log.e(currentWindowMetricsBounds.flattenToString())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.e("onSizeChanged [$w, $h, $oldw, $oldh]")
        this.w = w
        this.h = h
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}
