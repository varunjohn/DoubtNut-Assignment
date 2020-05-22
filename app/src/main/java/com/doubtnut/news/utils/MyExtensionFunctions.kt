package com.doubtnut.news.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.toHtmlSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}