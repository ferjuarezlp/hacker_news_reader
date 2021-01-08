package com.ferjuarez.readerhackernews.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
        val title: String?,
        val story_title: String?,
        val author: String,
        val created_at: String?,
        val created_at_i: Long,
        val url: String?,
        var deleted: Boolean,
        @PrimaryKey
    val objectID: Int
)