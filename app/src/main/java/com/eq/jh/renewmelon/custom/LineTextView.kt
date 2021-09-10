package com.eq.jh.renewmelon.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import com.eq.jh.renewmelon.utils.ScreenUtils


/**
 * Copyright (C) 2021 Kakao Inc. All rights reserved.
 *
 * Created by Invincible on 09/09/2021
 *
 */
class LineTextView(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {
    companion object {
        private const val TAG = "LineTextView"
    }

    private var screenWidth = 0

    private var linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 17 * resources.displayMetrics.density
    }

    init {
        screenWidth = ScreenUtils.getScreenWidth(context)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) {
            return
        }
        drawLine(canvas)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun drawLine(canvas: Canvas) {
        Log.d(TAG, "text : $text")
        val bounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        val textHeight = bounds.height()

        val yGap = ScreenUtils.dipToPixel(context, 3f)
        val lineGap = ScreenUtils.dipToPixel(context, 4f)
        val lineHeight = ScreenUtils.dipToPixel(context, 1f)
        repeat(5) { index ->
            var yAxis = 0f
            if (index == 0) {
                yAxis = (textHeight + yGap + (lineGap / 2)).toFloat()
                Log.d(TAG, "yAxis : index : $index, yAxis : $yAxis, yAxis + lineHeight : ${yAxis + lineHeight}")
                canvas.drawLine(0f, yAxis, screenWidth.toFloat(), yAxis + lineHeight, linePaint)
            } else {
                yAxis = (index + 1) * (textHeight + yGap + (lineGap / 2) + lineHeight).toFloat()
                Log.d(TAG, "yAxis : index : $index, yAxis : $yAxis, yAxis + lineHeight : ${yAxis + lineHeight}")
                canvas.drawLine(0f, yAxis, screenWidth.toFloat(), yAxis + lineHeight, linePaint)
            }
        }
    }
}