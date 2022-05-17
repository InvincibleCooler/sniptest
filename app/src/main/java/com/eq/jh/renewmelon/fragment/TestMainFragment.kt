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
        list.add("GradientDrawable 테스트")
        list.add("리스트 포커스 뷰 영상 테스트")
        list.add("리스트 포커스 뷰 영상 테스트2")
        list.add("리스트 포커스 뷰 영상 라운드코너 테스트")
        list.add("상세 뷰 영상 테스트")
        list.add("상세 뷰 영상 테스트2 - pager item fragment")
        list.add("알렌 rent house")
        list.add("이미지 움직이기")
        list.add("RecyclerView swipe menu")
        list.add("Algorithm")
        list.add("Nested RecyclerView keep position")
        list.add("Canvas Made Easy In Jetpack Compose")
        list.add("Parallax Album Test")
        list.add("Parallax Album Test2")
        list.add("draw line with textview")
        list.add("test canvas path")
        list.add("test canvas path2")

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
                                findNavController().navigate(R.id.fragment_gradient_drawable)
                            }
                            1 -> {
                                findNavController().navigate(R.id.fragment_test_list_focus)
                            }
                            2 -> {
                                findNavController().navigate(R.id.fragment_test_list_focus2)
                            }
                            3 -> {
                                findNavController().navigate(R.id.fragment_video_corner)
                            }
                            4 -> {
                                findNavController().navigate(R.id.fragment_test_detail)
                            }
                            5 -> {
                                findNavController().navigate(R.id.fragment_test_detail2)
                            }
                            6 -> {
                                findNavController().navigate(R.id.allen_rent_house)
                            }
                            7 -> {
                                findNavController().navigate(R.id.fragment_test_image_move)
                            }
                            8 -> {
                                findNavController().navigate(R.id.fragment_recycler_view_swipe_menu)
                            }
                            9 -> {
                                findNavController().navigate(R.id.fragment_algorithm)
                            }
                            10 -> {
                                findNavController().navigate(R.id.fragment_nested_recyclerview)
                            }
                            11 -> {
                                findNavController().navigate(R.id.fragment_canvas_jetpack)
                            }
                            12 -> {
                                findNavController().navigate(R.id.fragment_parallax)
                            }
                            13 -> {
                                findNavController().navigate(R.id.fragment_parallax2)
                            }
                            14 -> {
                                findNavController().navigate(R.id.fragment_draw_line_textview)
                            }
                            15 -> {
                                findNavController().navigate(R.id.fragment_test_canvas_path)
                            }
                            16 -> {
                                findNavController().navigate(R.id.fragment_test_canvas_path2)
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