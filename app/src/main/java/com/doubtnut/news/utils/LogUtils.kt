package com.doubtnut.news.utils

import android.util.Log

object LogUtils {

    private const val LOG_TAG = "varuntest"

    fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }
}