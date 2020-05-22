package com.doubtnut.news.utils

import java.text.SimpleDateFormat

object Utils {

    fun getNewsPublishTimeAgo(publishTime: String): String {
        var time = (System.currentTimeMillis()
                - SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(
            publishTime
        ).time) / 60000

        if (time > 60) {
            time /= 60
            return "$time hrs ago"
        } else {
            return "$time mins ago"
        }
    }

    fun getNewsPublishTimeFull(publishTime: String): String =
        SimpleDateFormat("h:mm a, MMM d, yyyy").format(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(
                publishTime
            )
        )

}