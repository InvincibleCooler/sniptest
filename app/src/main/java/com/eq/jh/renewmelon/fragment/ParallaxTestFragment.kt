package com.eq.jh.renewmelon.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.utils.ScreenUtils
import kotlin.math.max
import kotlin.math.min


@SuppressLint("LongLogTag")
class ParallaxTestFragment : BaseFragment() {
    companion object {
        private const val TAG = "ParallaxTestFragment"
    }

    private var topHeight = 0
    private var verticalDragRange = 0
    private var screenHeight = 0

    private lateinit var topHeaderLayout: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter

    private var scrollDy = 0

    private inner class ScrollLayoutManager(context: Context) : LinearLayoutManager(context) {
        override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
            val firstPosition = findFirstVisibleItemPosition()
            scrollDy += dy
            if (scrollDy >= screenHeight) {
                scrollDy = screenHeight
            } else if (scrollDy <= 0) {
                scrollDy = 0
            } else {
                // verticalDragRange 높이만큼 보정처리가 필요한것으로 판단됨.
//                scrollDy = min(max(0, scrollDy), verticalDragRange)
            }
            topHeaderLayout.translationY = -min(max(0f, scrollDy.toFloat()), verticalDragRange.toFloat())

//            if (firstPosition == 0) {
//                if (scrollDy in 0..CAN_MOVE_RANGE) {
//                    topHeaderLayout.translationY = -scrollDy.toFloat()
//                } else {
//                    topHeaderLayout.translationY = -CAN_MOVE_RANGE.toFloat()
//                }
//            }
            Log.d(TAG, "dy : $dy")
            Log.d(TAG, "scrollDy : $scrollDy")
//            Log.d(TAG, "topHeaderLayout.translationY : ${topHeaderLayout.translationY}")
//
//            Log.d(TAG, "firstPosition : $firstPosition")
            return super.scrollVerticallyBy(dy, recycler, state)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localAdapter = LocalAdapter()
        screenHeight = ScreenUtils.getScreenHeight(context)
        topHeight = ScreenUtils.dipToPixel(context, 400f)
        verticalDragRange = ScreenUtils.dipToPixel(context, 320f)
        Log.d(TAG, "screenHeight : $screenHeight")
        Log.d(TAG, "topHeight : $topHeight")
        Log.d(TAG, "verticalDragRange : $verticalDragRange")
        Log.d(TAG, "item size : ${ScreenUtils.dipToPixel(context, 80f)}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parallax, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topHeaderLayout = view.findViewById(R.id.top_header_layout)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.run {
            layoutManager = ScrollLayoutManager(context)
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