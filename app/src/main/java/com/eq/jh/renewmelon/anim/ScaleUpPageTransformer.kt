package com.eq.jh.renewmelon.anim

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.eq.jh.renewmelon.utils.ScreenUtils
import kotlin.math.abs


/**
 * Add a PageTransformer that translates the next and previous items horizontally
 * towards the center of the screen, which makes them visible
 * 참고 : viewpager2 scale animation https://ask.xiaolee.net/questions/1109758
 *
 * gravity : previous and next item의 focus된 아이템에 대한 위치
 * Gravity.BOTTOM, Gravity.CENTER 두개만 지원함
 */
class ScaleUpPageTransformer(context: Context, private val gravity: Int) : ViewPager2.PageTransformer {
    companion object {
        private const val TAG = "ScaleUpPageTransformer"

        /**
         * 디자인 가이드 기준
         * current item height : 399, next item height : 339
         * 현재 focus된 이미지의 높이와 옆의 아이템의 높이는 디자인 가이드상 around 0.85 (0.849624xxxxx), 다시 말하면 0.15의 높이 스케일링이 되면 1이됨.
         */
        private const val SCALE = 0.15f
    }

    // 55.5f = 30f + 30f * 0.85 which is (1 - 0.15)
    private val nextItemVisibleWidth = ScreenUtils.dipToPixel(context, 55.5f)
    private val horizontalMargin = ScreenUtils.dipToPixel(context, 73f)
    private val pageTranslationX = nextItemVisibleWidth + horizontalMargin

    override fun transformPage(page: View, position: Float) {
        if (gravity == Gravity.CENTER) {
            page.translationX = -pageTranslationX * position
            // Next line scales the item's width height. You can remove it if you don't want this effect
            page.scaleX = 1 - (SCALE * abs(position))
            page.scaleY = 1 - (SCALE * abs(position))
        } else if (gravity == Gravity.BOTTOM) {
            page.pivotX = (page.width.toFloat() / 2)
            page.pivotY = page.height.toFloat()
            page.translationX = -pageTranslationX * position
            page.scaleX = 1 - (SCALE * abs(position))
            page.scaleY = 1 - (SCALE * abs(position))
        }
    }
}