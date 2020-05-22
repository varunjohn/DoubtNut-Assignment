package com.doubtnut.news.model

import android.content.Context
import com.doubtnut.news.model.local.SharedPreferencesHelper
import com.doubtnut.news.model.remote.RestAPI
import com.doubtnut.news.utils.AppConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val context: Context, private val restAPI: RestAPI) {

    fun getNewsArticleList(): Observable<List<Article>> {

        val newsArticleList = SharedPreferencesHelper.getNewsArticleList();
        lateinit var articlesSource: Observable<List<Article>>

        if (newsArticleList == null) {
            articlesSource = restAPI.getNewsArticles()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap { response ->
                    if (response.status.equals(AppConstants.RestApiConstant.RESPONSE_SUCCESS)) {
                        SharedPreferencesHelper.saveNewsArticleList(response.articles)
                        Observable.just(response.articles)
                    } else {
                        Observable.error(Throwable("status false"))
                    }
                }
        } else {
            articlesSource = Observable.just(newsArticleList);
        }
        return articlesSource
    }

}