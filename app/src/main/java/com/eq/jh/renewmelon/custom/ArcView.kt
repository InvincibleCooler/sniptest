package com.eq.jh.renewmelon.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.eq.jh.renewmelon.R

/**
 *
 */
class ArcView(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {
    companion object {
        private const val TAG = "ArcView"
    }

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var filledColor: Int = -1

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ArcView, 0, 0)
        filledColor = a.getColor(R.styleable.ArcView_filled_color, Color.YELLOW)
        a.recycle()

        paint.apply {
            color = filledColor
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawArc(canvas)
    }

    private fun drawArc(canvas: Canvas?) {
        val c = canvas ?: return

        val rectF = RectF().apply {
            left = 0f + paddingStart
            top = 0f
            right = measuredWidth.toFloat() - paddingEnd
            bottom = measuredHeight.toFloat() * 2
        }

        c.drawArc(rectF, 180f, 180f, true, paint)
    }
}