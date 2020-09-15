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
class TestListFocusFragment : BaseFragment() {
    companion object {
        private const val TAG = "TestListFocusFragment"
    }

    private var player: TestPlayer? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var localAdapter: LocalAdapter

    var currentPlayableIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().let {
            localAdapter = LocalAdapter(it)
            createPlayer(it)
        }
    }

    private fun createPlayer(context: Context): TestPlayer? {
        Log.d(TAG, "createPlayer")
        player = TestPlayer(context).also {
            it.setCallback(object : TestPlayer.TestPlayerCallback {
                override fun onCompletion() {
                    Log.d(TAG, "onCompletion()")
                }

                override fun onPlaybackStateChanged(playWhenReady: Boolean, state: Int) {
                    Log.d(TAG, "onPlaybackStateChanged playWhenReady : $playWhenReady, state : ${player?.stateName(state)}")
                    if (state == Player.STATE_READY) {
                        if (!playWhenReady) {
                            player?.start()

                            val adapter = recyclerView.adapter as LocalAdapter
                            val item = adapter.items[currentPlayableIndex]
                            player?.seekTo(item.seekPosition)
                        }
                    }
                }

                override fun onError(error: String) {
                    Log.d(TAG, "onError error : $error")
                }
            })
        }
        return player
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
//                Log.d(TAG, "idle") // 이 때부터 플레이를 해야함
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

//                    Log.d(TAG, "i : $i, percentage : $percentage")
                    if (percentage > maxPercentage) {
                        playableList.add(i)
                    }
                }
            }

            if (currentPlayableIndex != playableList.min()) {
                val adapter = recyclerView.adapter as LocalAdapter
                if (currentPlayableIndex != -1) {
                    // 여기서 video stop
                    val currentPosition = player?.getCurrentPosition() ?: 0
                    player?.pause()
                    player?.release()
                    player = null

                    // 다음 재생을 위해서 포지션 저장
                    val item = adapter.items[currentPlayableIndex]
                    item.seekPosition = currentPosition
                    adapter.items[currentPlayableIndex] = item
                }

                currentPlayableIndex = playableList.min() ?: -1

                if (currentPlayableIndex != -1) {
                    // 여기서 video play
                    val vh = recyclerView.findViewHolderForAdapterPosition(currentPlayableIndex) as LocalAdapter.ItemViewHolder
                    val item = adapter.items[currentPlayableIndex]
                    if (!item.url.isBlank()) {
                        if (player == null) {
                            createPlayer(requireContext())
                        }

                        vh.playerView.player = player?.getPlayer()
                        player?.setDataSource(Uri.parse(item.url), true)
                    }
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
        data.url = "https://techslides.com/demos/sample-videos/small.mp4" // 접근 안되는 영상 onError error : Unable to connect
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
                }
            }
        }

        inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTv: TextView = view.findViewById(R.id.tv_title)
            val playerView: PlayerView = view.findViewById(R.id.player_view)
        }
    }
}