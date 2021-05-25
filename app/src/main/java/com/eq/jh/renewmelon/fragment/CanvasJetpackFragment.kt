package com.eq.jh.renewmelon.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eq.jh.renewmelon.R
import com.eq.jh.renewmelon.ext.enforceSingleScrollDirection
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("LongLogTag")
class CanvasJetpackFragment : BaseFragment() {
    companion object {
        private const val TAG = "CanvasJetpackFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_canvas_jetpack, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        loadNetwork(
//            success = { msg ->
//                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//            },
//            error = { msg ->
//                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//            }
//        )

        isOddNumber {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNetwork(
        success: (successMsg: String) -> Unit,
        error: (errorMsg: String) -> Unit
    ) {
        val i = Random().nextInt()
        Log.d("LJH", "i : $i")
        if (i % 2 == 0) {
            success.invoke("짝수")
        } else {
            error.invoke("홀수")
        }
    }

    private fun isOddNumber(oddNumber: (Boolean) -> Unit) {
        val i = Random().nextInt()
        if (i % 2 == 0) {
            oddNumber.invoke(false)
        } else {
            oddNumber.invoke(true)
        }
    }
}