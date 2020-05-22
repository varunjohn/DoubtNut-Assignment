package com.doubtnut.news.model

data class Response(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)