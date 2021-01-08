package com.ferjuarez.readerhackernews.persistance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ferjuarez.readerhackernews.data.models.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll() : LiveData<List<Article>>

    @Query("SELECT * FROM articles WHERE objectID = :id")
    fun getArticle(id: Int): LiveData<Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articles: List<Article>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: Article)

    @Update()
    suspend fun update(article: Article)
}