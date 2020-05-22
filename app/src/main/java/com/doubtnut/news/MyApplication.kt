package com.doubtnut.news

import android.app.Application
import com.doubtnut.news.dagger.component.AppComponent
import com.doubtnut.news.dagger.component.DaggerAppComponent
import com.doubtnut.news.model.local.SharedPreferencesHelper
import com.doubtnut.news.utils.AppConstants
import com.doubtnut.news.utils.ToastUtils
import com.google.gson.Gson

class MyApplication : Application() {

    companion object {
        lateinit var diComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        ToastUtils.initialize(this)
        SharedPreferencesHelper.initialize(this, Gson())
        diComponent = initializeDI()
    }

    fun initializeDI(): AppComponent {
        return DaggerAppComponent.builder().contextModule(this)
            .networkModule(AppConstants.RestApiConstant.BASE_URL).build()
    }
}