package com.doubtnut.news.model

import android.content.Context
import com.doubtnut.news.model.local.SharedPreferencesHelper
import com.doubtnut.news.model.remote.RestAPI
import com.doubtnut.news.utils.AppConstants
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val context: Context, private val restAPI: RestAPI) {

    fun getNewsArticleList(): Observable<List<Article>> {

        val newsArticleList = SharedPreferencesHelper.getNewsArticleList();

        val observableOnSubscribe: ObservableOnSubscribe<List<Article>> =
            ObservableOnSubscribe { emitter ->
                run {
                    if (newsArticleList == null || System.currentTimeMillis() - SharedPreferencesHelper.getNewsArticleListSaveTime() > 60000) {

                        if (newsArticleList != null) {
                            emitter.onNext(newsArticleList)
                        }

                        restAPI.getNewsArticles()
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.computation())
                            .observeOn(Schedulers.computation())
                            .subscribe(
                                { response ->
                                    if (response.status == AppConstants.RestApiConstant.RESPONSE_SUCCESS) {
                                        SharedPreferencesHelper.saveNewsArticleList(response.articles)
                                        SharedPreferencesHelper.saveNewsArticleListSaveTime(System.currentTimeMillis())
                                        emitter.onNext(response.articles)
                                    } else {
                                        if (newsArticleList == null)
                                            emitter.onError(Throwable("status false"))
                                        else
                                            emitter.onNext(newsArticleList)
                                    }
                                }, { error ->
                                    if (newsArticleList == null)
                                        emitter.onError(error)
                                    else
                                        emitter.onNext(newsArticleList)
                                })
                    } else {
                        emitter.onNext(newsArticleList)
                    }
                }
            }

        return Observable.create(observableOnSubscribe)
    }

}