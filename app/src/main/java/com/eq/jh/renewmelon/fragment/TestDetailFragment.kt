package com.eq.jh.renewmelon.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.anim.ScaleUpPageTransformer
import com.eq.jh.renewmelon.custom.OnClickListener
import com.eq.jh.renewmelon.custom.OnPinchListener
import com.eq.jh.renewmelon.custom.GestureRelativeLayout
import com.eq.jh.renewmelon.utils.ScreenUtils


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class TestDetailFragment : BaseFragment() {
    companion object {
        private const val TAG = "TestDetailFragment"
    }

    private lateinit var viewPager2: ViewPager2
    private lateinit var testAdapter: TestAdapter

    // related to pop layout
    private lateinit var popLayout: View
    private lateinit var popViewpager2: ViewPager2
    private lateinit var popNameTv: TextView
    private lateinit var popCloseTv: TextView
    private lateinit var popAdapter: PopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        testAdapter = TestAdapter()
        popAdapter = PopAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager2 = view.findViewById(R.id.viewpager2)
        viewPager2.run {
//            offscreenPageLimit = 1
            isUserInputEnabled = false
            adapter = testAdapter
        }

        val testData = TestDataSet()
        testData.title = "영상 타이틀"
        testData.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        val dataList = arrayListOf(testData)
        testAdapter.items = dataList
        testAdapter.notifyDataSetChanged()

        // pop layout
        popLayout = view.findViewById(R.id.pop_layout)
        popViewpager2 = view.findViewById(R.id.pop_viewpager2)
        popNameTv = view.findViewById(R.id.pop_name_tv)
        popCloseTv = view.findViewById(R.id.pop_close_tv)

        popViewpager2.run {
            offscreenPageLimit = 1 // You need to retain one page on each side so that the next and previous items are visible
            adapter = popAdapter
            setPageTransformer(ScaleUpPageTransformer(context, Gravity.BOTTOM))
            addItemDecoration(PopItemDecoration(ScreenUtils.dipToPixel(context, 73f)))
        }

        popCloseTv.setOnClickListener {
            showPopLayout(false)
        }
    }

    private inner class TestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var items = mutableListOf<TestDataSet>()
            set(value) {
                field.clear()
                field = value
            }

        override fun getItemCount(): Int {
            return 3
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return TestHolder(LayoutInflater.from(context).inflate(R.layout.test_pager_item, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val vh = holder as TestHolder

            vh.tvTitle.text = "우리 나라 좋은 나라"

            (vh.itemView as GestureRelativeLayout).run {
                scaleListener = object : OnPinchListener {
                    override fun onPinchIn() {
                        showPopLayout(true)
                    }

                    override fun onPinchOut() {
                    }
                }
                clickListener = object : OnClickListener {
                    override fun onClick() {
                        Log.d(TAG, "click play or pause")
                    }
                }
            }
        }

        private inner class TestHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tv_title)
        }
    }

    private class TestDataSet {
        var title = "영상 타이틀"
        var url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    }

    // related to pop layout
    inner class PopAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val viewTypeItem = 1

        // 일단 테스트로 String으로 하고 나중에 데이터 set으로 바꾸자
        var items = mutableListOf<String>()
            set(value) {
                field.clear()
                field = value
            }

        override fun getItemCount(): Int {
            return 5
        }

        override fun getItemViewType(position: Int): Int {
            return viewTypeItem
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return PopHolder(LayoutInflater.from(context).inflate(R.layout.pop_viewpager2_item, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                viewTypeItem -> {
                    val vh = holder as PopHolder
//                val data = items[position]

                    if (position % 2 == 0) {
                        vh.trackTitleTv.text = "See You Again"
                        vh.trackCreatorTv.text = "Cimo Frankel(@loneflower12)"
                    } else {
                        vh.trackTitleTv.text = "See You Again See You Again See You Again"
                        vh.trackCreatorTv.text = "Cimo Frankel(@loneflower12) Cimo Frankel(@loneflower12)"
                    }

                    vh.itemView.setOnClickListener {
                        Log.d(TAG, "click position : $position")
                    }
                }
            }
        }

        private inner class PopHolder(view: View) : RecyclerView.ViewHolder(view) {
            val thumbIv: ImageView = view.findViewById(R.id.thumb_iv)
            val trackTitleTv: TextView = view.findViewById(R.id.title_tv)
            val trackCreatorTv: TextView = view.findViewById(R.id.creator_tv)
        }
    }

    private inner class PopItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = margin
            outRect.left = margin
        }
    }

    private fun showPopLayout(isShow: Boolean) {
        popLayout.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}