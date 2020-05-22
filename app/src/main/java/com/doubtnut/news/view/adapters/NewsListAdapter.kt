package com.doubtnut.news.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubtnut.news.R
import com.doubtnut.news.model.Article
import com.doubtnut.news.view.viewholders.NewsListVewHolder

class NewsListAdapter(val clickListener : AdapterClickListener<Article>) : RecyclerView.Adapter<NewsListVewHolder>() {

    private val list = ArrayList<Article>()

    fun addArticles(articles: List<Article>) {
        list.addAll(articles)
    }

    fun clearList() {
        list.clear()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListVewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_article_item, parent, false)
        return NewsListVewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListVewHolder, position: Int) {
        val article: Article = list[position]
        holder.bind(article, clickListener)
    }

}