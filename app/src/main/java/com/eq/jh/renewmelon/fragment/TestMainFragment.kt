package com.eq.jh.renewmelon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eq.jh.renewmelon.R


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class TestMainFragment : BaseFragment() {
    companion object {
        private const val TAG = "TestMainFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localAdapter = LocalAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = localAdapter
            setHasFixedSize(true)
        }

        val list = mutableListOf<String>()
        list.add("리스트 포커스 뷰 영상 테스트")
        list.add("리스트 포커스 뷰 영상 테스트2")
        list.add("리스트 포커스 뷰 영상 라운드코너 테스트")
        list.add("상세 뷰 영상 테스트")

        localAdapter.setItems(list)
        localAdapter.notifyDataSetChanged()
    }

    private inner class LocalAdapter() :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val viewTypeItem = 1

        private var items = mutableListOf<String>()

        fun setItems(items: MutableList<String>) {
            this.items.clear()
            this.items = items
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemViewType(position: Int): Int {
            return viewTypeItem
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.test_main_listitem, parent, false)
            )
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            when (viewHolder.itemViewType) {
                viewTypeItem -> {
                    val vh = viewHolder as ItemViewHolder
                    val data = items[position]

                    vh.titleTv.text = data

                    vh.itemView.setOnClickListener {
                        when (position) {
                            0 -> {
                                findNavController().navigate(R.id.fragment_test_list_focus)
                            }
                            1 -> {
                                findNavController().navigate(R.id.fragment_test_list_focus2)
                            }
                            2 -> {
                                findNavController().navigate(R.id.fragment_video_corner)
                            }
                            3 -> {
                                findNavController().navigate(R.id.fragment_test_detail)
                            }
                        }
                    }
                }
            }
        }

        private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTv: TextView = view.findViewById(R.id.tv_title)
        }
    }
}