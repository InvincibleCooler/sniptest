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
        val num1: IntArray = intArrayOf(60, 50)
        val num2: IntArray = intArrayOf(30, 70)
        val num3: IntArray = intArrayOf(60, 30)
        val num4: IntArray = intArrayOf(40, 80)
//        val num5: IntArray = intArrayOf(90, 20)
        val sizes = arrayOf(num1, num2, num3, num4)
//        sizes.add(num5)

        Log.d(TAG, "answer : ${solution(sizes)}")
    }

    fun solution(sizes: Array<IntArray>): Int {
        var answer: Int = 0

        val list = sizes.flatMap {
            it.toList()
        }

        val max = list.max()

        var pair = 0
        var isFirstMax = false
        val otherSizeList = ArrayList<Int>()

        sizes.forEach {
            val first = it[0]
            val second = it[1]

            if (first == max) {
                pair = second
                isFirstMax = true
            } else if (second == max) {
                pair = first
                isFirstMax = false
            }
        }

        sizes.forEach {
            var first: Int
            var second: Int
            if (isFirstMax) {
                first = it[0]
                second = it[1]
            } else {
                first = it[1]
                second = it[0]
            }

            if (pair < second) {
                otherSizeList.add(if (first >= second) second else first)
            }
        }
        answer = max!! * otherSizeList.max()!!
        return answer
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