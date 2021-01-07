package com.ferjuarez.hackernews.networking

import com.ferjuarez.readerhackernews.networking.ArticleService
import com.ferjuarez.readerhackernews.networking.BaseDataSource
import javax.inject.Inject

class ArticlesApiDataSource @Inject constructor(private val articlesService: ArticleService): BaseDataSource() {
    suspend fun getArticles() = getResult { articlesService.getAllArticles() }
}