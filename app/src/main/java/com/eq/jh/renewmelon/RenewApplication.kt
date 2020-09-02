package com.eq.jh.renewmelon

import android.app.Application
import android.content.Context


/**
 * Copyright (C) 2020 Kakao Inc. All rights reserved.
 *
 * Created by Invincible on 26/08/2020
 *
 */
class RenewApplication : Application() {
    companion object {
        const val TAG = "RenewApplication"

        private lateinit var instance: RenewApplication

        fun getInstance(): RenewApplication {
            return instance
        }

        fun getContext(): Context {
            return instance
        }
    }

    init {
        instance = this
    }
}