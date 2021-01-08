package com.ferjuarez.readerhackernews.repository

import com.ferjuarez.readerhackernews.data.get
import com.ferjuarez.readerhackernews.data.models.Article
import com.ferjuarez.readerhackernews.networking.ArticlesApiDataSource
import com.ferjuarez.readerhackernews.persistance.ArticleDao
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val remoteDataSource: ArticlesApiDataSource,
    private val localDataSource: ArticleDao
) {

    fun getArticles() = get(
        databaseQuery = { localDataSource.getAll() },
        networkCall = { remoteDataSource.getArticles() },
        saveCallResult = { localDataSource.insertAll(it.hits) }
    )

    suspend fun deleteArticle(article: Article) {
        article.deleted = true
        localDataSource.update(article)
    }
}