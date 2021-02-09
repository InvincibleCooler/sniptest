package com.eq.jh.renewmelon.swipe

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.eq.jh.renewmelon.fragment.RecyclerViewSwipeMenuFragment
import kotlin.math.max
import kotlin.math.min

/**
 * 참고 : https://velog.io/@trycatch98/Android-RecyclerView-Swipe-Menu
 */
class SwipeHelperCallback2 : ItemTouchHelper.Callback() {
    companion object {
        private const val TAG = "SwipeHelperCallback"

        const val GONE = 0
        const val LEFT_VISIBLE = 1
        const val RIGHT_VISIBLE = 2
    }

    var buttonState = GONE
    private var preButtonState = GONE

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    var clamp = 0f

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        Log.d(TAG, "clearView")
        currentDx = 0f
        getDefaultUIUtil().clearView(getView(viewHolder))
        previousPosition = viewHolder.adapterPosition
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        Log.d(TAG, "onSelectedChanged actionState : $actionState")
        super.onSelectedChanged(viewHolder, actionState)
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        Log.d(TAG, "getSwipeEscapeVelocity")
        return defaultValue * 10
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        Log.d(TAG, "getSwipeThreshold")
        val isClamped = getTag(viewHolder)
        if (buttonState == RIGHT_VISIBLE) {
            setTag(viewHolder, !isClamped && currentDx <= -clamp)
        } else if (buttonState == LEFT_VISIBLE) {
            setTag(viewHolder, !isClamped && currentDx >= clamp)
        }
        return 2f
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)
            currentDx = x
            getDefaultUIUtil().onDraw(c, recyclerView, view, x, dY, actionState, isCurrentlyActive)
        }
    }

    private fun clampViewPositionHorizontal(view: View, dX: Float, isClamped: Boolean, isCurrentlyActive: Boolean): Float {
        Log.d(TAG, "clampViewPositionHorizontal dX : $dX")

        if (dX < 0f) { // 오른쪽에서 왼쪽으로 swipe : <----
            buttonState = RIGHT_VISIBLE
        } else if (dX > 0f) {
            buttonState = LEFT_VISIBLE
        } else {
            buttonState = GONE
        }

        var returnValue = 0f
        if (buttonState == RIGHT_VISIBLE) {
            val min: Float = -view.width.toFloat() / 2
            val max = 0f

            val x = if (isClamped) {
                if (isCurrentlyActive) dX - clamp else -clamp
            } else {
                dX
            }
            Log.d(TAG, "RIGHT_VISIBLE : ${min(max(min, x), max)}")
            returnValue = min(max(min, x), max)
        } else if (buttonState == LEFT_VISIBLE) {
            val min = 0f
            val max: Float = view.width.toFloat() / 2

            val x = if (isClamped) {
                if (isCurrentlyActive) dX + clamp else clamp
            } else {
                dX
            }
            Log.d(TAG, "LEFT_VISIBLE : ${min(max(min, x), max)}")
            returnValue = min(max(min, x), max)
        }

        if (buttonState == GONE && preButtonState == LEFT_VISIBLE) {
            if (isClamped) {
                returnValue = clamp
            } else {
                returnValue = 0f
            }
        } else if (buttonState == GONE && preButtonState == RIGHT_VISIBLE) {
            if (isClamped) {
                returnValue = -clamp
            } else {
                returnValue = 0f
            }
        }
        Log.d(TAG, "buttonState : ${buttonState}")
        Log.d(TAG, "preButtonState : ${preButtonState}")
        Log.d(TAG, "returnValue : ${returnValue}")
        preButtonState = buttonState
        return returnValue
    }

    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        viewHolder.itemView.tag = isClamped
    }

    private fun getTag(viewHolder: RecyclerView.ViewHolder): Boolean {
        return viewHolder.itemView.tag as? Boolean ?: false
    }

    private fun getView(viewHolder: RecyclerView.ViewHolder): View {
        return (viewHolder as RecyclerViewSwipeMenuFragment.LocalAdapter.ItemViewHolder).swipeLayout
    }

    fun removePreviousClamp(recyclerView: RecyclerView) {
        if (currentPosition == previousPosition) {
            return
        }
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).translationX = 0f
            setTag(viewHolder, false)
            previousPosition = null
        }
    }
}