package com.ferjuarez.readerhackernews.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ferjuarez.readerhackernews.data.models.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll() : LiveData<List<Article>>

    @Query("SELECT * FROM articles WHERE objectID = :id")
    fun getArticle(id: Int): LiveData<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)


}