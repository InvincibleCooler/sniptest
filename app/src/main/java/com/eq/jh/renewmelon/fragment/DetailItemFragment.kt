package com.eq.jh.renewmelon.fragment

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.anim.ScaleUpPageTransformer
import com.eq.jh.renewmelon.custom.OnClickListener
import com.eq.jh.renewmelon.custom.OnPinchListener
import com.eq.jh.renewmelon.custom.GestureRelativeLayout
import com.eq.jh.renewmelon.playback.TestPlayer
import com.eq.jh.renewmelon.utils.ScreenUtils
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class DetailItemFragment : BaseFragment() {
    companion object {
        private const val TAG = "DetailItemFragment"
        private const val ARG_URL = "argUrl"

        fun newInstance(url: String?): DetailItemFragment {
            return DetailItemFragment().also { f ->
                f.arguments = Bundle().also { b ->
                    b.putString(ARG_URL, url)
                }
            }
        }
    }

    private lateinit var playerView: PlayerView

    private var url: String? = null
    private var player: TestPlayer? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inState = savedInstanceState ?: arguments
        inState?.let {
            url = inState.getString(ARG_URL)
        }

        createPlayer(requireActivity())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_URL, url)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_pager_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerView = view.findViewById(R.id.player_view)
        playerView.setOnClickListener {
            Log.d(TAG, "click play or pause")
        }

        playerView.player = player?.getPlayer()

        if (!url.isNullOrBlank()) {
            player?.setDataSource(Uri.parse(url), true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.release()
        player = null
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }
}