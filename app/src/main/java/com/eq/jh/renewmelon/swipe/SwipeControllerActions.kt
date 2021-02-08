package com.eq.jh.renewmelon.swipe


/**
 * Copyright (C) 2021 Kakao Inc. All rights reserved.
 *
 * Created by Invincible on 05/02/2021
 *
 */
abstract class SwipeControllerActions {
    open fun onLeftClicked(position: Int) {}
    open fun onRightClicked(position: Int) {}
}