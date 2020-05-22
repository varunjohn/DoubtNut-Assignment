package com.doubtnut.news.view.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doubtnut.news.MyApplication
import com.doubtnut.news.databinding.ActivityNewsListBinding
import com.doubtnut.news.model.Article
import com.doubtnut.news.utils.ToastUtils
import com.doubtnut.news.view.adapters.AdapterClickListener
import com.doubtnut.news.view.adapters.NewsListAdapter
import javax.inject.Inject

class NewsListActivity : AppCompatActivity(), AdapterClickListener<Article> {

    @Inject
    lateinit var context: Context

    private lateinit var activity: Activity

    private lateinit var newsListViewModel: NewsListViewModel

    private lateinit var viewBinding: ActivityNewsListBinding

    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Injecting the Dependencies
        MyApplication.diComponent.inject(this)

        // View Binding for to easily access activity's view
        viewBinding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Just for Reference
        activity = this

        // Refering viewmodel for this activity
        newsListViewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)

        // Setting up recycler and its adapter
        newsListAdapter = viewBinding.recyclerViewNewsList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = NewsListAdapter(activity as AdapterClickListener<Article>)
        }.adapter as NewsListAdapter

        // Rx pattern for getting list of news from Rest client or Database
        newsListViewModel.getNewsArticleList().subscribe({
            newsListAdapter.addArticles(it)
            newsListAdapter.notifyDataSetChanged()
            viewBinding.progressBar.visibility = View.GONE
        }, {
            ToastUtils.show("can't fetch news, try later...")
        })
    }

    // Adapters click events will be handled here
    override fun onClick(view: View, model: Article, viewHolder: RecyclerView.ViewHolder) {
        NewsDetailActivity.open(activity, model)
    }
}
