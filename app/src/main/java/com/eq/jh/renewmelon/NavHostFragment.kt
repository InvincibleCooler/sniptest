package com.eq.jh.renewmelon

import androidx.navigation.NavController
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.NavHostFragment


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 *
 * Created by Invincible on 26/08/2020
 *
 */
class NavHostFragment : NavHostFragment() {
    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)

        navController.navigatorProvider.addNavigator(
            DialogFragmentNavigator(requireContext(), childFragmentManager)
        )
    }
}