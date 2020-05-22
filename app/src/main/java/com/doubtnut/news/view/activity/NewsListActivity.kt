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
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NewsListActivity : AppCompatActivity(), AdapterClickListener<Article>, View.OnClickListener {

    @Inject
    lateinit var context: Context

    private lateinit var activity: Activity

    private lateinit var newsListViewModel: NewsListViewModel

    private lateinit var viewBinding: ActivityNewsListBinding

    private lateinit var newsListAdapter: NewsListAdapter

    private lateinit var disposable: Disposable

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
        getNewsList()

        viewBinding.retry.setOnClickListener(this)
    }

    private fun getNewsList() {
        disposable = newsListViewModel.getNewsArticleList().subscribe({
            newsListAdapter.clearList()
            newsListAdapter.addArticles(it)
            newsListAdapter.notifyDataSetChanged()
            viewBinding.progressBar.visibility = View.GONE
        }, {
            ToastUtils.showLong("Can't fetch news, try later...")
            viewBinding.progressBar.visibility = View.GONE
            viewBinding.retry.visibility = View.VISIBLE
        })
    }

    // Adapters click events will be handled here
    override fun onClick(view: View, model: Article, viewHolder: RecyclerView.ViewHolder) {
        NewsDetailActivity.open(activity, model)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onClick(v: View?) {
        when (v) {
            viewBinding.retry -> {
                viewBinding.retry.visibility = View.GONE
                viewBinding.progressBar.visibility = View.VISIBLE
                getNewsList()
            }
        }
    }
}
