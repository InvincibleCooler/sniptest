package com.eq.jh.renewmelon.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.utils.ScreenUtils
import kotlin.math.max
import kotlin.math.min


@SuppressLint("LongLogTag")
class ParallaxTest2Fragment : BaseFragment() {
    companion object {
        private const val TAG = "ParallaxTest2Fragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parallax2, container, false)
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
        for (i in 0 until 20) {
            list.add("$i 번째")
        }

        localAdapter.setItems(list)
        localAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localAdapter = LocalAdapter()
    }

    private inner class LocalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val viewTypeSpace = Int.MIN_VALUE
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
            return when (position) {
                0 -> {
                    viewTypeSpace
                }
                else -> {
                    viewTypeItem
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                viewTypeSpace -> {
                    FirstSpaceItemViewHolder(LayoutInflater.from(context).inflate(R.layout.parallax_space_listitem, parent, false))
                }
                else -> {
                    ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.parallax_listitem, parent, false))
                }
            }
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

        private inner class FirstSpaceItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTv: TextView = view.findViewById(R.id.tv_title)
        }
    }
}