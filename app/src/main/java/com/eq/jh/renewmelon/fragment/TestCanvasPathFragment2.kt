package com.eq.jh.renewmelon.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eq.jh.renewmelon.R


@SuppressLint("LongLogTag")
class TestCanvasPathFragment2 : BaseFragment() {
    companion object {
        private const val TAG = "TestCanvasPathFragment2"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_canvas_path2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}