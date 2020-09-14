package com.eq.jh.renewmelon.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.RelativeLayout
import kotlin.math.abs


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
interface OnPinchListener {
    fun onPinchIn()
    fun onPinchOut()
}

interface OnClickListener {
    fun onClick()
}

class GestureRelativeLayout(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs), OnGestureListener {
    companion object {
        private const val TAG = "GestureRelativeLayout"
    }

    private val gestureScaleDetector: ScaleGestureDetector
    private val gestureDetector: GestureDetector
    var scaleListener: OnPinchListener? = null
    var clickListener: com.eq.jh.renewmelon.custom.OnClickListener? = null

    private val floatList = arrayListOf<Float>()

    init {
        gestureScaleDetector = ScaleGestureDetector(context, ScaleListen())
        gestureDetector = GestureDetector(context, this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureScaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }


    private inner class ScaleListen : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            floatList.clear()
            return true
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            val scaleFactor = detector?.scaleFactor ?: 0f
            if (java.lang.Float.isNaN(scaleFactor) || java.lang.Float.isInfinite(scaleFactor)) {
                return false
            }

            floatList.add(scaleFactor)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            super.onScaleEnd(detector)
            var pinchInCount = 0
            var pinchOutCount = 0

            for (value in floatList) {
                if (abs(1f - value) > 0.05) { // 쓰레기 데이타는 필터함
                    if ((1f - value) > 0) {
                        pinchInCount++
                    } else if ((1f - value) < 0) {
                        pinchOutCount++
                    }
                }
            }

            if (pinchInCount > pinchOutCount) {
                scaleListener?.onPinchIn()
            } else {
                scaleListener?.onPinchOut()
            }
        }
    }

    // related to OnGestureListener
    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        clickListener?.onClick()
        return true
    }

    override fun onDown(e: MotionEvent?) = true

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float) = true

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float) = true

    override fun onLongPress(e: MotionEvent?) {
    }
}