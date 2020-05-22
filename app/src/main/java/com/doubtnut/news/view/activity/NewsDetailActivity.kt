package com.doubtnut.news.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubtnut.news.MyApplication
import com.doubtnut.news.R
import com.doubtnut.news.dagger.module.GlideApp
import com.doubtnut.news.databinding.ActivityNewsDetailBinding
import com.doubtnut.news.model.Article
import com.doubtnut.news.utils.Utils
import com.doubtnut.news.utils.toHtmlSpanned
import javax.inject.Inject

class NewsDetailActivity : AppCompatActivity() {

    // A pattern to launch the activity, open function will be overloaded..
    companion object {
        private const val KEY_ARTICLE_OBJECT = "article"

        fun open(context: Context, article: Article) {
            context.startActivity(
                Intent(context, NewsDetailActivity::class.java).putExtra(
                    KEY_ARTICLE_OBJECT,
                    article
                )
            )
        }
    }

    @Inject
    lateinit var context: Context

    private lateinit var activity: Activity

    private lateinit var viewBinding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Injecting the Dependencies
        MyApplication.diComponent.inject(this)

        // View Binding for to easily access activity's view
        viewBinding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Just for Reference
        activity = this

        // Setting Title
        supportActionBar?.title = "Article Details"

        // Get Serialized article to show its info in this activity
        val article = intent.getSerializableExtra(KEY_ARTICLE_OBJECT) as Article

        // Bind news article with activity views
        bindArticle(article)
    }

    private fun bindArticle(article: Article) {

        viewBinding.title.text = article.title
        viewBinding.description.text = article.description?.toHtmlSpanned();

        viewBinding.authorSourceTime.text =
            "Author: ${article.author ?: "Unknown"}\n${article.source.name} | ${Utils.getNewsPublishTimeFull(
                article.publishedAt
            )}"

        var url: String? = article.urlToImage

        if (url != null) {
            if (article.source.name.contains("cnbc", true))
                url = "$url&w=500"

            GlideApp.with(context)
                .load(url)
                .into(viewBinding.image)
        } else {
            GlideApp.with(context)
                .load(R.drawable.news_placeholder)
                .into(viewBinding.image)
        }

        val content: String? = article.content

        if (content != null) {
            viewBinding.content.text = (article.content.substring(0, article.content.lastIndexOf('[')) + "more")
        }

        viewBinding.sourceUrl.text = "<u>${article.url}</u>".toHtmlSpanned()
    }
}
