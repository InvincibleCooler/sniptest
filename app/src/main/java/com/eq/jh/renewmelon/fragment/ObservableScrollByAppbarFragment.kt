package com.eq.jh.renewmelon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eq.jh.renewmelon.R


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class ObservableScrollByAppbarFragment : BaseFragment() {
    companion object {
        private const val TAG = "ObservableScrollByAppbarFragment"
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
        return inflater.inflate(R.layout.observable_scroll, container, false)
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
        list.add("1")
        list.add("2")
        list.add("3")
        list.add("4")
        list.add("5")
        list.add("6")
        list.add("7")
        list.add("8")
        list.add("9")
        list.add("10")
//        list.add("11")
//        list.add("12")
//        list.add("13")
//        list.add("14")
//        list.add("15")
//        list.add("16")
//        list.add("17")
//        list.add("18")

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
                }
            }
        }

        private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTv: TextView = view.findViewById(R.id.tv_title)
        }
    }
}