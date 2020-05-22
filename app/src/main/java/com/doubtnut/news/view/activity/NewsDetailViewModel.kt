package com.doubtnut.news.view.activity

import android.content.Context
import androidx.lifecycle.ViewModel
import com.doubtnut.news.MyApplication
import com.doubtnut.news.model.Article
import com.doubtnut.news.model.Repository
import com.doubtnut.news.model.remote.RestAPI
import io.reactivex.Observable
import javax.inject.Inject

class NewsDetailViewModel : ViewModel() {

    @Inject
    lateinit var restAPI: RestAPI

    @Inject
    lateinit var context: Context

    init {
        MyApplication.diComponent.inject(this)
    }

    fun getNewsArticleList(): Observable<List<Article>> {
        return Repository(context, restAPI).getNewsArticleList()
    }
}