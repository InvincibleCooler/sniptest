package com.eq.jh.renewmelon.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.eq.jh.renewmelon.R

/**
 * Music Dna에서 사용하는 BG
 */
class MixedArcView(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {
    companion object {
        private const val TAG = "MixedArcView"
    }

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var circleFilledColor: Int = -1
    private var mixFilledColor: Int = -1
    private var arcFilledColor: Int = -1

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MixedArcView, 0, 0)
        circleFilledColor = a.getColor(R.styleable.MixedArcView_circle_filled_color, Color.YELLOW)
        mixFilledColor = a.getColor(R.styleable.MixedArcView_mix_filled_color, Color.YELLOW)
        arcFilledColor = a.getColor(R.styleable.MixedArcView_arc_filled_color, Color.YELLOW)
        a.recycle()

        paint.apply {
            color = circleFilledColor
        }
    }

    private val path = Path()
    private val oval = RectF()

    private var width = 0f
    private var height = 0f
    private var radius = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val c = canvas ?: return

        width = measuredWidth.toFloat()
        height = measuredHeight.toFloat()
        radius = (width / 2) / 2

        drawCircle(c)
        drawMixedShape(c)
        drawHalfCircle(canvas)
    }

    private fun drawCircle(c: Canvas) {
        c.drawCircle(radius, radius, radius, paint)
    }

    private fun drawMixedShape(c: Canvas) {
        // 좌 하단
        val yAix = height - (height / 3)
        path.moveTo(0F, height)
        path.lineTo(0F, yAix)
        path.addArc(0F, radius * 2, (width / 2), (height / 3) * 2, 180f, 180f)
        path.lineTo((width / 2), height)
        path.lineTo(0F, height)
        c.drawPath(path, paint)

        // 우 상단
        path.reset()
        path.moveTo(width / 2, 0F)
        path.lineTo(width / 2, radius)
        path.addArc(width / 2, 0F, width, radius * 2, 180f, -180f)
        path.lineTo(width, 0F)
        path.lineTo(width / 2, 0F)
        c.drawPath(path, paint)
    }

    private fun drawHalfCircle(c: Canvas) {
        val top = radius * 2
        val eachHeight = (height - radius * 2) / 3
        val bottom = top + eachHeight * 2
        Log.d(TAG, "height : $height")
        Log.d(TAG, "top : $top")
        Log.d(TAG, "height - top : ${height - top}")
        Log.d(TAG, "(real height / 3) : $bottom")

        oval.left = width / 2
        oval.top = top
        oval.right = width
        oval.bottom = bottom
        c.drawArc(oval, 180f, 180f, true, paint)

        c.save()
        c.translate(0F, eachHeight)
        c.drawArc(oval, 180f, 180f, true, paint)
        c.restore()

        c.save()
        c.translate(0F, eachHeight * 2)
        c.drawArc(oval, 180f, 180f, true, paint)
        c.restore()
    }
}