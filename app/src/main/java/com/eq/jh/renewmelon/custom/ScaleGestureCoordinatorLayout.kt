package com.eq.jh.renewmelon.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlin.math.abs


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 *
 * Created by Invincible on 04/09/2020
 *
 */
class ScaleGestureCoordinatorLayout(context: Context, attrs: AttributeSet?) : CoordinatorLayout(context, attrs) {
    companion object {
        private const val TAG = "ScaleGestureCoordinatorLayout"
    }

    private val gestureDetector: ScaleGestureDetector
    private val floatList = arrayListOf<Float>()
    private var scaleListener: OnPinchListener? = null

    fun setOnPinchListener(scaleListener: OnPinchListener?) {
        this.scaleListener = scaleListener
    }

    init {
        gestureDetector = ScaleGestureDetector(context, ScaleListen())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
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


}