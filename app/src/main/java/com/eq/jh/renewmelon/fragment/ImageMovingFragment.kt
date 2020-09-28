package com.eq.jh.renewmelon.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.utils.ScreenUtils
import kotlin.math.abs
import kotlin.math.max


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class ImageMovingFragment : BaseFragment() {
    companion object {
        private const val TAG = "ImageMovingFragment"
    }

    private lateinit var movingImageView: ImageView
    private var screenWidth = 0
    private var screenHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            screenWidth = ScreenUtils.getScreenWidth(it)
            screenHeight = ScreenUtils.getScreenHeight(it)
        }

        Log.d("LJH", "screenWidth : $screenWidth, screenHeight : $screenHeight")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_image_move, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movingImageView = view.findViewById(R.id.move_iv)
        val filepath: String = Environment.getExternalStorageDirectory().toString() + "/Pictures/KakaoTalk/test.jpg"
        Log.d("LJH", "file : $filepath")

        Glide.with(this)
            .asBitmap()
//            .load("https://pds.joins.com/news/component/htmlphoto_mmdata/202006/08/b4f86d29-cc3a-4fda-ba3b-c60eb4f95c75.jpg")
            .load("https://dimg.donga.com/wps/NEWS/IMAGE/2020/04/22/100760919.2.jpg")
//            .load(Uri.fromFile(File(filepath)))
//            .into(object : CustomTarget<Bitmap>((screenWidth * 1.15f).toInt(), (screenHeight * 1.15f).toInt()) {
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val resourceWidth = resource.width
                    val resourceHeight = resource.height

                    val xScale = if (resourceWidth != 0) screenWidth.toFloat() / resourceWidth.toFloat() else 0f
                    val yScale = if (resourceHeight != 0) screenHeight.toFloat() / resourceHeight.toFloat() else 0f
                    Log.d(TAG, "screenWidth : $screenWidth, screenHeight : $screenHeight")
                    Log.d(TAG, "resourceWidth : $resourceWidth, resourceHeight : $resourceHeight, xScale : $xScale, yScale : $yScale")

                    if (xScale == 0f || yScale == 0f) {
                        return
                    }

                    val resizedScale = max(xScale, yScale) * 1.15f // 1.15배 이미지 scale up
                    val modifiedWidth = resourceWidth.toFloat() * resizedScale
                    val modifiedHeight = resourceHeight.toFloat() * resizedScale
                    Log.d(TAG, "resizedScale : $resizedScale")

                    movingImageView.setImageBitmap(resource)
                    movingImageView.pivotX = 0f
                    movingImageView.pivotY = 0f
                    movingImageView.scaleX = resizedScale
                    movingImageView.scaleY = resizedScale
                    movingImageView.y = -(abs(modifiedHeight - screenHeight) / 2)
                    Log.d(TAG, "modifiedHeight : $modifiedHeight, screenHeight : $screenHeight")
                    Log.d(TAG, "abs(modifiedHeight - screenHeight) : ${abs(modifiedHeight - screenHeight)}")
                    Log.d(TAG, "-(abs(modifiedHeight - screenHeight) / 2) : ${-(abs(modifiedHeight - screenHeight) / 2)}")

                    val anim = ObjectAnimator.ofFloat(movingImageView, "translationX", (screenWidth - modifiedWidth))
                    anim.run {
                        duration = 30000
                        repeatCount = ValueAnimator.INFINITE
                        repeatMode = ValueAnimator.REVERSE
                        start()
                    }
                }
            })
    }
}