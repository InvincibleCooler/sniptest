package com.eq.jh.renewmelon.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.ext.setCircleColor
import com.eq.jh.renewmelon.ext.setRectangleColor
import com.eq.jh.renewmelon.utils.ScreenUtils


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class GradientDrawableFragment : BaseFragment() {
    companion object {
        private const val TAG = "GradientDrawableFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gradient_drawable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivShapeCircle = view.findViewById<View>(R.id.iv_shape_circle)
        (ivShapeCircle.background as GradientDrawable).run {
//            setCircleColor(fillColor = "#36dde8")
//            setCircleColor(fillColor = ContextCompat.getColor(view.context, R.color.red))
//            setCircleColor(fillColor = Color.RED)
//
//            setCircleColor(fillColor = "#36dde8", strokeWith = 0, strokeColor = "#777777")
//            setCircleColor(fillColor = "#36dde8", strokeWith = ScreenUtils.dipToPixel(view.context, 2f), strokeColor = "#777777")
//            setCircleColor(fillColor = "#36dde8", strokeWith = ScreenUtils.dipToPixel(view.context, 2f), strokeColor = Color.RED)
            setCircleColor(fillColor = "#36dde8", strokeWith = ScreenUtils.dipToPixel(view.context, 2f), ContextCompat.getColor(view.context, R.color.red))
        }

        val ivShapeRectangle = view.findViewById<View>(R.id.iv_shape_rec)
        (ivShapeRectangle.background as GradientDrawable).run {
            setRectangleColor(fillColor = "#36dde8", radius = ScreenUtils.dipToPixel(view.context, 4f))
        }
    }
}