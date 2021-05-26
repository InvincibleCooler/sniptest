package com.eq.jh.renewmelon.utils

import android.content.Context


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 *
 * Created by Invincible on 14/09/2020
 *
 */
class ScreenUtils {
    companion object {
        fun dipToPixel(context: Context?, dip: Float): Int {
            context?.let {
                val density = it.resources.displayMetrics.density
                return (dip * density).toInt()
            }
            return 0
        }

        fun pixelToDip(context: Context?, pixel: Int): Int {
            context?.let {
                val density = context.resources.displayMetrics.density
                return (pixel.toFloat() / density).toInt()
            }
            return 0
        }

        fun getScreenWidth(context: Context?): Int {
            context?.let {
                return context.resources.displayMetrics.widthPixels
            }
            return 0
        }

        fun getScreenHeight(context: Context?): Int {
            context?.let {
                return context.resources.displayMetrics.heightPixels
            }
            return 0
        }

        fun getStatusBarHeight(context: Context?): Int {
            context?.let {
                val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
                return if (resourceId > 0) {
                    context.resources.getDimensionPixelSize(resourceId)
                } else {
                    0
                }
            }
            return 0
        }

        fun getNavigationBarHeight(context: Context?): Int {
            context?.let {
                val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
                return if (resourceId > 0) {
                    context.resources.getDimensionPixelSize(resourceId)
                } else {
                    0
                }
            }
            return 0
        }
    }
}