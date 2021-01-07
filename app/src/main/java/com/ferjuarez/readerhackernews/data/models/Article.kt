package com.ferjuarez.readerhackernews.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    val title: String,
    val storyTitle: String,
    val author: String,
    val createdAt: String,
    val url: String,
    @PrimaryKey
    val objectID: Int
)