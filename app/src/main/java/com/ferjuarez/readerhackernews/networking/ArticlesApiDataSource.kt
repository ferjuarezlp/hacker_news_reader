package com.ferjuarez.readerhackernews.networking

import javax.inject.Inject

class ArticlesApiDataSource @Inject constructor(private val articlesService: ArticleService): BaseDataSource() {
    suspend fun getArticles() = getResult { articlesService.getAllArticles() }
}