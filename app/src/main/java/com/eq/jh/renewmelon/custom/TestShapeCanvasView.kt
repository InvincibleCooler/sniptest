package com.eq.jh.renewmelon.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.eq.jh.renewmelon.utils.ScreenUtils


class TestShapeCanvasView(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {
    companion object {
        private const val TAG = "TestShapeCanvasView"
    }

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
    }

    private var screenWidth = 0
    private var screenHeight = 0

    private val path = Path()
    private val oval = RectF()

    init {
        screenWidth = ScreenUtils.getScreenWidth(context)
        screenHeight = ScreenUtils.getScreenHeight(context)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawHalfCircle(canvas)
    }

    private fun drawHalfCircle(canvas: Canvas?) {
        val c = canvas ?: return
        val count = c.save()

        oval.left = 0f
        oval.top = 0f
        oval.right = measuredWidth.toFloat()
        oval.bottom = measuredHeight.toFloat() * 2

//        path.moveTo(0F, measuredHeight.toFloat())
//        path.addArc(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), -180f, 180f)

        c.drawArc(oval, 180f, 180f, true, paint)
//        canvas.drawPath(path, paint)
        c.restoreToCount(count)
    }
}