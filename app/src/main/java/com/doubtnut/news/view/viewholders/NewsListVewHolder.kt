package com.doubtnut.news.view.viewholders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.doubtnut.news.R
import com.doubtnut.news.dagger.module.GlideApp
import com.doubtnut.news.databinding.NewsArticleItemBinding
import com.doubtnut.news.model.Article
import com.doubtnut.news.utils.Utils
import com.doubtnut.news.view.adapters.AdapterClickListener

class NewsListVewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val viewBinding = NewsArticleItemBinding.bind(view)

    private var context: Context = itemView.context

    fun bind(
        article: Article,
        clickListener: AdapterClickListener<Article>
    ) {

        viewBinding.title.text = article.title
        viewBinding.source.text = article.source.name

        viewBinding.time.text = Utils.getNewsPublishTimeAgo(article.publishedAt)


        var url: String? = article.urlToImage

        if (url != null) {

            if (article.source.name.contains("cnbc", true))
                url = "$url&w=500"

            GlideApp.with(context)
                .load(url)
                .override(500, 500)
                .into(viewBinding.image)
        } else {
            GlideApp.with(context)
                .load(R.drawable.news_placeholder)
                .into(viewBinding.image)
        }

        itemView.setOnClickListener { view -> clickListener.onClick(view, article, this) }
    }
}