package com.doubtnut.news.model.remote

import com.doubtnut.news.model.Response
import io.reactivex.Observable
import retrofit2.http.GET

interface RestAPI {

    @GET("/v2/top-headlines?country=us&category=business&apiKey=0380a21c5792428eb7251ebde4dd06b8")
    fun getNewsArticles(): Observable<Response>

}