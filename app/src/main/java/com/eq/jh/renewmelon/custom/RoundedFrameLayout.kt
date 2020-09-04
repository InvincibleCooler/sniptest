package com.eq.jh.renewmelon.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 * https://github.com/developer-anees/android-round-camera2video-preview/blob/master/app/src/main/java/in/round/camera/preview/RoundedFrameLayout.java
 * style처리해야 될것 같은데, radius를 xml에서 주면 잘 안되네. 하여든 CardView가 훨씬 좋기 때문에 그냥 패스
 */
class RoundedFrameLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var radius = 0f
    private val path = Path()
    private val rect = RectF()

    init {
        radius = attrs?.getAttributeFloatValue(null, "corner_radius", 0f) ?: 0f
    }

    override fun onDraw(canvas: Canvas) {
        val savedState: Int = canvas.save()
        val w = width.toFloat()
        val h = height.toFloat()
        path.reset()
        rect[0f, 0f, w] = h
        path.addRoundRect(rect, radius, radius, Path.Direction.CCW)
        path.close()
        val debug: Boolean = canvas.clipPath(path)
        super.onDraw(canvas)
        canvas.restoreToCount(savedState)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // compute the mPath
        val centerX = w / 2f // calculating half width
        val centerY = h / 2f // calculating half height
        path.reset()
        path.addCircle(centerX, centerY, Math.min(centerX, centerY), Path.Direction.CW)
        path.close()
    }

    override fun dispatchDraw(canvas: Canvas) {
        val save: Int = canvas.save()
        canvas.clipPath(path)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(save)
    }
}