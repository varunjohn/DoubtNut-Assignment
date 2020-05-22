package com.doubtnut.news.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.toHtmlSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
