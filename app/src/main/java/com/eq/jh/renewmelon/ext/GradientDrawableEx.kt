package com.eq.jh.renewmelon.ext

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

/**
 * shape 처리
 */
inline val GradientDrawable.defaultColor: String
    get() = "#FFFFFF"

/**
 * fillColor : 16진수 스트링 eg) #36dde8
 * String 인 경우 : # 포함 16진수 스트링 eg) #36dde8
 * Int 인 경우 : ResId
 */
fun GradientDrawable.setCircleColor(
    fillColor: Any,
    strokeWith: Int = 0,
    strokeColor: Any? = null
) {
    when (fillColor) {
        is String -> {
            setColor(Color.parseColor(fillColor))
        }
        is Int -> {
            setColor(fillColor)
        }
        else -> {
            throw IllegalArgumentException("fill color is a wrong type")
        }
    }

    if (strokeWith != 0) {
        when (strokeColor) {
            is String -> {
                setStroke(strokeWith, Color.parseColor(strokeColor))
            }
            is Int -> {
                setStroke(strokeWith, strokeColor)
            }
            else -> {
                throw IllegalArgumentException("stroke color is a wrong type")
            }
        }
    }
}

fun GradientDrawable.setRectangleColor(
    fillColor: Any,
    strokeWith: Int = 0,
    strokeColor: Any? = null,
    radius: Int = 0
) {
    cornerRadius = radius.toFloat()
    setCircleColor(fillColor, strokeWith, strokeColor)
}


