package com.eq.jh.renewmelon.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eq.jh.renewmelon.R


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 */
class AlgorithmFragment : BaseFragment() {
    companion object {
        private const val TAG = "AlgorithmFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.algorithm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val nums: IntArray = intArrayOf(7, 11, 2, 15)
        val nums: IntArray = intArrayOf(15, 11, 7, 2)
        val target = 9

        val valueArray = twoSum(nums, target)
        valueArray.forEach {
            Log.d(TAG, "valueArray it : $it")
        }
    }

    private fun twoSum(nums: IntArray, target: Int): IntArray {
        val result = ArrayList<Int>()
        val hashMap = hashMapOf<Int, Int>() // value, index
        nums.forEachIndexed { i, num ->
            val diff = target - num
            if (hashMap.containsKey(num)) {
                result.add(i)
                result.add(hashMap[num]!!)
            }
            hashMap[diff] = i
        }
        return result.toIntArray()
    }

//    private fun twoSum(nums: IntArray, target: Int): IntArray { // first solution
//        val result = ArrayList<Int>()
//
//        loop@ for (i in nums.indices) {
//            for (j in (i + 1) until nums.size) {
//                if (nums[i] + nums[j] == target) {
//                    result.add(i)
//                    result.add(j)
//                    break@loop
//                }
//            }
//        }
//        return result.toIntArray()
//    }
}