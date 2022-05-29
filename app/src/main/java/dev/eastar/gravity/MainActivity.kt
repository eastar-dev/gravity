package dev.eastar.gravity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toRect
import androidx.core.graphics.toRectF
import androidx.core.view.GravityCompat
import androidx.window.layout.WindowMetricsCalculator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContentView(FrameLayout(context).apply {
//            setBackgroundColor(0x55ff0000)
            addView(GravityView(context).apply {
//                setBackgroundColor(0x55ff0000)
            })
        })
        obtainWindowMetrics()
    }

    private fun obtainWindowMetrics() {
        val wmc = WindowMetricsCalculator.getOrCreate()
        val currentWindowMetricsBounds = wmc.computeCurrentWindowMetrics(this).bounds
        Log.e(currentWindowMetricsBounds.flattenToString())
    }
}

class GravityView(context: Context) : View(context) {
    private val outRect = mutableListOf<Rect>()
    private var centerRect: RectF = RectF()
    private var centerRect2: RectF = RectF()
    private var h: Int = 0
    private var w: Int = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.e("onSizeChanged [$w, $h, $oldw, $oldh]")
        this.w = w
        this.h = h
        super.onSizeChanged(w, h, oldw, oldh)
        centerRect = RectF(w / 2F, h / 2F, w / 2F, h / 2F).apply {
            inset((100).dpf, (100).dpf)
            sort()
        }

        centerRect2 = RectF(centerRect).apply { offset(10.dpf,20.dpf) }

        GravityCompat.apply(Gravity.START or Gravity.TOP, 70.dp, 40.dp, centerRect.toRect(), outRect.let { it.add(Rect()); it.last() }, LAYOUT_DIRECTION_LTR)
        GravityCompat.apply(Gravity.START or Gravity.TOP, 70.dp, 40.dp, centerRect.toRect(), 10.dp, 10.dp, outRect.let { it.add(Rect()); it.last() }, LAYOUT_DIRECTION_LTR)

        GravityCompat.apply(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 70.dp, 40.dp, centerRect.toRect(), outRect.let { it.add(Rect()); it.last() }, LAYOUT_DIRECTION_LTR)
        GravityCompat.apply(
            Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, //정렬 방식
            70.dp, 40.dp, // 70x40 박스를 넣고 싶을때
            centerRect.toRect(), //기준박스
            0.dp, 10.dp, //내부위치로 offset
            outRect.let { it.add(Rect()); it.last() }, //return
            LAYOUT_DIRECTION_LTR
        )

        //모르겠다.
//        GravityCompat.applyDisplay(Gravity.END or Gravity.BOTTOM or Gravity.DISPLAY_CLIP_VERTICAL , centerRect.toRect(), centerRect2.toRect(), LAYOUT_DIRECTION_LTR)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        canvas.drawRect(centerRect, "${centerRect.width()}x${centerRect.height()}", Color.BLUE)
//        canvas.drawRect(centerRect2, "${centerRect2.width()}x${centerRect2.height()}", Color.CYAN)
        outRect.forEach {
            canvas.drawRect(it.toRectF(), "${it.width()}x${it.height()}", Color.RED)
        }
    }
}


data class PointInfo(
    var tag: String,
    var x: Float,
    var y: Float,
)

private val radius = 100f

private val paintLine = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = 0x55ff0000.I
    style = Paint.Style.STROKE
    textSize = 14.dpf
    strokeWidth = 1.dpf
}

fun Canvas.drawRect(rect: RectF, tag: String = "", color: Int? = null) {
    if (color != null) paintLine.color = color
    drawRect(rect, paintLine)
    drawLine(rect.left, rect.top, rect.right, rect.bottom, paintLine)
    drawLine(rect.left, rect.bottom, rect.right, rect.top, paintLine)
    drawText(tag, rect.left, rect.top + paintLine.textSize, paintLine)
}

private fun Canvas.drawPoints(vararg pointInfo: PointInfo) {
    pointInfo.forEach {
        val x = it.x
        val y = it.y
        drawLineX(y)
        drawLineY(x)
        paintLine.color = Color.LTGRAY
        paintLine.textSize = 10.dpf
        paintLine.textAlign = Paint.Align.RIGHT
        drawCircle(x, y, radius, paintLine)
        drawText(it.tag, x + radius, y - radius, paintLine)
    }
}

private fun Canvas.drawLineX(y: Float, color: Int = Color.RED) {
    save()
    translate(0f, y)
    paintLine.color = color
    drawLine(0f, 0f, SYSTEM_WIDTH.F, 0f, paintLine)
    restore()

}

private fun Canvas.drawLineY(x: Float, color: Int = Color.RED) {
    save()
    translate(x, 0f)
    paintLine.color = color
    drawLine(0f, 0f, 0f, SYSTEM_HEIGHT.F, paintLine)
    restore()
}