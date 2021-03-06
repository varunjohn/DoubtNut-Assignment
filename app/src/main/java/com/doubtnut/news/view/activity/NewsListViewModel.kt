package com.doubtnut.news.view.activity

import android.content.Context
import androidx.lifecycle.ViewModel
import com.doubtnut.news.MyApplication
import com.doubtnut.news.model.Article
import com.doubtnut.news.model.Repository
import com.doubtnut.news.model.remote.RestAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel : ViewModel() {

    @Inject
    lateinit var restAPI: RestAPI

    @Inject
    lateinit var context: Context

    init {
        MyApplication.diComponent.inject(this)
    }

    fun getNewsArticleList(): Observable<List<Article>> {
        return Repository(context, restAPI).getNewsArticleList()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCleared() {
        super.onCleared()
    }
}