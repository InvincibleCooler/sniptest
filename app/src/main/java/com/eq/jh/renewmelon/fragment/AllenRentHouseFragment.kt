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
class AllenRentHouseFragment : BaseFragment() {
    companion object {
        private const val TAG = "AllenRentHouseFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.allen_rent_house, container, false)
    }
}