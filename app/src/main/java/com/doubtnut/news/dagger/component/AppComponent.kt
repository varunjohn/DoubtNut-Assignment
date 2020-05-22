package com.doubtnut.news.dagger.component

import android.app.Application
import com.doubtnut.news.dagger.module.ContextModule
import com.doubtnut.news.dagger.module.NetworkModule
import com.doubtnut.news.view.activity.NewsDetailActivity
import com.doubtnut.news.view.activity.NewsDetailViewModel
import com.doubtnut.news.view.activity.NewsListActivity
import com.doubtnut.news.view.activity.NewsListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ContextModule::class])
interface AppComponent {

    fun inject(injection: NewsListActivity)
    fun inject(injection: NewsListViewModel)
    fun inject(injection: NewsDetailActivity)
    fun inject(injection: NewsDetailViewModel)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun networkModule(baseUrl: String): Builder

        @BindsInstance
        fun contextModule(app: Application): Builder

        fun build(): AppComponent
    }
}