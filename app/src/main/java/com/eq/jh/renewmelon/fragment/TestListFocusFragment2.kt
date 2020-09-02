package com.eq.jh.renewmelon.fragment

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.playback.TestPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class TestListFocusFragment2 : BaseFragment() {
    companion object {
        private const val TAG = "LJH"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter

    var currentPlayableIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().let {
            localAdapter = LocalAdapter(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_list_focus, container, false)
    }

    private val scrollListener: OnScrollListener = object : OnScrollListener() {
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                (recyclerView.adapter)?.notifyDataSetChanged()
//            }
//        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition()
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            var maxPercentage = 0.7 // 70% 이상이 보여야만 play 가능 (요구사항)
            val globalVisibleRect = Rect()
            recyclerView.getGlobalVisibleRect(globalVisibleRect)

            val playableList = mutableListOf<Int>()
            for (i in firstPosition..lastPosition) {
                val view = layoutManager.findViewByPosition(i)
                view?.let {
                    val percentage = getVisibleHeightPercentage(it)

                    if (percentage > maxPercentage) {
                        playableList.add(i)
                    }
                }
            }

            if (currentPlayableIndex != playableList.min()) {
                Log.d(TAG, "before index : $currentPlayableIndex")
                val adapter = recyclerView.adapter as LocalAdapter
                if (currentPlayableIndex != -1) {
                    // 여기서 video stop
                    val item = adapter.items[currentPlayableIndex]
                    item.whenReady = false
                    adapter.items[currentPlayableIndex] = item
                }

                currentPlayableIndex = playableList.min() ?: -1

                Log.d(TAG, "after index : $currentPlayableIndex")
                if (currentPlayableIndex != -1) {
                    // 여기서 video play
                    val vh = recyclerView.findViewHolderForAdapterPosition(currentPlayableIndex) as LocalAdapter.ItemViewHolder
                    val item = adapter.items[currentPlayableIndex]
                    if (!item.url.isBlank()) {
                        vh.player?.setDataSource(Uri.parse(item.url), true)
                    }
                }
                if (!recyclerView.isComputingLayout) {
                    adapter.notifyDataSetChanged()
                }
            }
        }

        private fun getVisibleHeightPercentage(view: View): Double {
            val itemRect = Rect()
            val isParentViewEmpty = view.getLocalVisibleRect(itemRect)
            val visibleHeight = itemRect.height().toDouble()
            val height = view.measuredHeight
            val viewVisibleHeightPercentage = visibleHeight / height
            return if (isParentViewEmpty) {
                viewVisibleHeightPercentage
            } else {
                0.0
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = localAdapter
            setHasFixedSize(true)
            addOnScrollListener(scrollListener)
        }

        val list = mutableListOf<DataWrapper>()
        var data = DataWrapper()
        data.title = "영상 1"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        data = DataWrapper()
        data.title = "영상 2"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        data = DataWrapper()
        data.title = "영상 3"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        data = DataWrapper()
        data.title = "영상 4"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        data = DataWrapper()
        data.title = "영상 5"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        data = DataWrapper()
        data.title = "영상 6"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        data = DataWrapper()
        data.title = "영상 7"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        data = DataWrapper()
        data.title = "영상 8"
        data.url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        list.add(data)

        localAdapter.items = list
        localAdapter.notifyDataSetChanged()
    }

    private inner class DataWrapper {
        var title = ""
        var url = ""
        var seekPosition = 0L
        var whenReady = false
    }

    private inner class LocalAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val viewTypeItem = 1

        var items = mutableListOf<DataWrapper>()
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
            return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.test_list_forcus_listitem, parent, false))
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            when (viewHolder.itemViewType) {
                viewTypeItem -> {
                    val vh = viewHolder as ItemViewHolder
                    val data = items[position]

                    vh.titleTv.text = data.title

                    val player = vh.player

                    Log.d(TAG, "position : $position, whenReady : ${data.whenReady}")
                    if (data.whenReady) {
//                        Log.d(TAG, "position : $position, load seek position : ${data.seekPosition}")
                        player.start()
                        player.seekTo(data.seekPosition)
                    } else {
                        player.pause()
                        data.whenReady = false
                        data.seekPosition = player.getCurrentPosition()
                        items[position] = data // position 저장이 안됨
//                        if (data.seekPosition > 0) {
//                            Log.d(TAG, "position : $position, save seek position : ${data.seekPosition}")
//                        }
                    }
                }
            }
        }

        inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTv: TextView = view.findViewById(R.id.tv_title)
            val playerView: PlayerView = view.findViewById(R.id.player_view)
            val player: TestPlayer

            init {
                player = TestPlayer(context).apply {
                    setCallback(object : TestPlayer.TestPlayerCallback {
                        override fun onCompletion() {
                            Log.d(TAG, "onCompletion()")
                        }

                        override fun onPlaybackStateChanged(playWhenReady: Boolean, state: Int) {
                            Log.d(TAG, "onPlaybackStateChanged playWhenReady : $playWhenReady, state : ${stateName(state)}")
                            if (state == Player.STATE_READY) {
                                if (!playWhenReady) {
                                    val item = items[currentPlayableIndex]
                                    item.whenReady = true
                                    if (!recyclerView.isComputingLayout) {
                                        notifyDataSetChanged()
                                    }
                                }
                            }
                        }

                        override fun onError(error: String) {
                            Log.d(TAG, "onError error : $error")
                        }
                    })
                }
                playerView.player = player.getPlayer()
            }
        }
    }
}