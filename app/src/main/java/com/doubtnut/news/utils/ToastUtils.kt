package com.doubtnut.news.utils;

import android.content.Context
import android.widget.Toast

object ToastUtils {

    lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context
    }

    fun show(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLong(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
