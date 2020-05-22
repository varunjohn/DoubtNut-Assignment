package com.doubtnut.news.model.local;

import android.content.Context
import android.content.SharedPreferences
import com.doubtnut.news.model.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesHelper {

    private const val PREF_NAME = "doubtnut_pref"
    private const val NEWS_SAVE_TIME = "news_save_time"
    private const val NEWS_LIST_JSON = "news_list_json"


    private lateinit var pref: SharedPreferences
    private lateinit var gson: Gson

    fun initialize(context: Context, gson: Gson) {
        pref = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        this.gson = gson
    }

    fun saveNewsArticleList(list: List<Article>) {
        val type = object : TypeToken<List<Article>>() {}.type
        pref.edit().putString(NEWS_LIST_JSON, gson.toJsonTree(list, type).toString()).apply()
    }

    fun getNewsArticleList(): List<Article>? {
        val type = object : TypeToken<List<Article>>() {}.type
       return pref.getString(NEWS_LIST_JSON, null)?.let { gson.fromJson(it, type) }
    }

    fun saveNewsArticleListSaveTime(time: Long) {
        pref.edit().putLong(NEWS_SAVE_TIME, time).apply()
    }

    fun getNewsArticleListSaveTime(): Long {
        return pref.getLong(NEWS_SAVE_TIME, 0);
    }


}
