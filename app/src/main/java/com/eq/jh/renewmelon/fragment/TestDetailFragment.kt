package com.eq.jh.renewmelon.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.custom.OnPinchListener
import com.eq.jh.renewmelon.custom.ScaleGestureRelativeLayout


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class TestDetailFragment : BaseFragment() {
    companion object {
        private const val TAG = "LJH"
    }

    private lateinit var viewPager2: ViewPager2
    private lateinit var testAdapter: TestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testAdapter = TestAdapter()

        viewPager2 = view.findViewById(R.id.viewpager2)
        viewPager2.run {
            isUserInputEnabled = false
            adapter = testAdapter
        }

        val testData = TestDataSet()
        testData.title = "영상 타이틀"
        testData.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        val dataList = arrayListOf(testData)
        testAdapter.items = dataList
        testAdapter.notifyDataSetChanged()
    }

    private inner class TestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var items = mutableListOf<TestDataSet>()
            set(value) {
                field.clear()
                field = value
            }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return TestHolder(LayoutInflater.from(context).inflate(R.layout.test_pager_item, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val vh = holder as TestHolder

            vh.tvTitle.text = "우리 나라 좋은 나라"

            (vh.itemView as ScaleGestureRelativeLayout).setOnPinchListener(object : OnPinchListener {
                override fun onPinchIn() {
                    Log.d(TAG, "onPinchIn")
                }

                override fun onPinchOut() {
                    Log.d(TAG, "onPinchOut")
                }
            })
        }

        private inner class TestHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tv_title)
        }
    }

    private class TestDataSet {
        var title = "영상 타이틀"
        var url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    }
}