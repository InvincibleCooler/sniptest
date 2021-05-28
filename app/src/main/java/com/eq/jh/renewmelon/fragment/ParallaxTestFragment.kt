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
class ParallaxTestFragment : BaseFragment() {
    companion object {
        private const val TAG = "ParallaxTestFragment"
    }

    private var topHeight = 0
    private var verticalDragRange = 0
    private var screenHeight = 0
    private var itemHeight = 0
    private var navigationBarHeight = 0

    private lateinit var topHeaderLayout: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter

    private var scrollDy = 0

    private inner class ScrollLayoutManager(context: Context) : LinearLayoutManager(context) {
        override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
            scrollDy += dy
            val totalItemHeight = getTotalItemHeight()

            if (scrollDy >= topHeight + (itemHeight * 19) + navigationBarHeight - screenHeight) {
                scrollDy = topHeight + (itemHeight * 19) + navigationBarHeight - screenHeight
            } else if (scrollDy <= 0) {
                scrollDy = 0
            }
            topHeaderLayout.translationY = -min(max(0f, scrollDy.toFloat()), verticalDragRange.toFloat())

//            Log.d(TAG, "dy : $dy")
//            Log.d(TAG, "scrollDy : $scrollDy")
            return super.scrollVerticallyBy(dy, recycler, state)
        }

        fun getTotalItemHeight(): Int { // 화면에 안그려지는 부분 처리가 불가능
//            Log.d(TAG, "childCount : $childCount")
            var totalHeight = 0

            for (i in 0 until childCount) {
                val view = findViewByPosition(i)
                if (view != null) {
                    totalHeight += getDecoratedMeasuredHeight(view)
                }
            }
            return totalHeight
        }

        override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
            Log.d(TAG, "onLayoutChildren state?.isPreLayout : ${state?.isPreLayout}")
            Handler(Looper.getMainLooper()).post {
                var totalHeight = 0

                for (i in 0 until childCount) {
                    val view = getChildAt(i)
                    if (view != null) {
                        totalHeight += getDecoratedMeasuredHeight(view)
                    }
                }
                Log.d(TAG, "onLayoutChildren totalHeight : $totalHeight")
            }
            if (state?.isPreLayout == true) {
            }
            super.onLayoutChildren(recycler, state)
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    override fun onPause() {
        super.onPause()
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    private val globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val manager = recyclerView.layoutManager
        if (manager is ScrollLayoutManager) {
            Log.d(TAG, "aaa : ${manager.getTotalItemHeight()}")
        }
        Log.d(TAG, "bbb : ${recyclerView.computeVerticalScrollExtent()}")
        Log.d(TAG, "ccc : ${recyclerView.computeVerticalScrollOffset()}")
        Log.d(TAG, "ddd : ${recyclerView.computeVerticalScrollRange()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localAdapter = LocalAdapter()
        screenHeight = ScreenUtils.getScreenHeight(context)
        topHeight = ScreenUtils.dipToPixel(context, 400f)
        verticalDragRange = ScreenUtils.dipToPixel(context, 320f)
        itemHeight = ScreenUtils.dipToPixel(context, 80f)
        navigationBarHeight = ScreenUtils.getNavigationBarHeight(context)
        Log.d(TAG, "screenHeight : $screenHeight")
        Log.d(TAG, "topHeight : $topHeight")
        Log.d(TAG, "verticalDragRange : $verticalDragRange")
        Log.d(TAG, "item size : ${ScreenUtils.dipToPixel(context, 80f)}")
        Log.d(TAG, "status : ${ScreenUtils.getStatusBarHeight(context)}")
        Log.d(TAG, "navigationBarHeight : $navigationBarHeight")
        Log.d(TAG, "item height : ${topHeight + itemHeight * 19}")
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