package com.eq.jh.renewmelon.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.swipe.SwipeController
import com.eq.jh.renewmelon.swipe.SwipeHelperCallback
import com.eq.jh.renewmelon.utils.ScreenUtils


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
@SuppressLint("LongLogTag")
class RecyclerViewSwipeMenuFragment : BaseFragment() {
    companion object {
        private const val TAG = "RecyclerViewSwipeMenuFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter
    private lateinit var swipeController: SwipeController
    private var buttonWidth = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localAdapter = LocalAdapter()
        swipeController = SwipeController()

        context?.let {
            buttonWidth = ScreenUtils.dipToPixel(it, 120f).toFloat()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycler_view_swipe_menu, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeHelperCallback = SwipeHelperCallback().apply {
            clamp = buttonWidth
            menuMode = SwipeHelperCallback.MENU_MODE_RIGHT
        }

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = localAdapter
            setHasFixedSize(true)
            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val list = mutableListOf<String>()
        list.add("AAAAAAAAAAAAAAAAAAAA")
        list.add("BBBBBBBBBBBBBBBBBBBB")
        list.add("CCCCCCCCCCCCCCCCCCCC")
        list.add("DDDDDDDDDDDDDDDDDDDD")
        list.add("EEEEEEEEEEEEEEEEEEEE")
        list.add("FFFFFFFFFFFFFFFFFFFF")
        list.add("GGGGGGGGGGGGGGGGGGGG")
        list.add("HHHHHHHHHHHHHHHHHHHH")
        localAdapter.items = list
        localAdapter.notifyDataSetChanged()
    }

    inner class LocalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val viewTypeItem = 1

        var items = mutableListOf<String>()
            set(value) {
                field.clear()
                field = value
            }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemViewType(position: Int): Int {
            return viewTypeItem
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_view_swipe_menu_item, parent, false)
            )
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            when (viewHolder.itemViewType) {
                viewTypeItem -> {
                    val vh = viewHolder as ItemViewHolder
                    val data = items[position]

                    vh.titleTv.text = data

                    vh.left1Tv.setOnClickListener {
                        Log.d(TAG, "left1Tv")
                    }
                    vh.left2Tv.setOnClickListener {
                        Log.d(TAG, "left2Tv")
                    }
                    vh.imageIv.setOnClickListener {
                        Log.d(TAG, "imageIv")
                    }
                    vh.itemView.setOnClickListener {
                        Log.d(TAG, "itemView")
                    }
                }
            }
        }

        inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val left1Tv: TextView = view.findViewById(R.id.left1_tv)
            val left2Tv: TextView = view.findViewById(R.id.left2_tv)
            val imageIv: ImageView = view.findViewById(R.id.image_iv)
            val titleTv: TextView = view.findViewById(R.id.tv_title)
            val swipeLayout: View = view.findViewById(R.id.swipe_layout)
        }
    }
}