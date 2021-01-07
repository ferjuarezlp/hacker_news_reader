package com.ferjuarez.readerhackernews.networking

import com.ferjuarez.readerhackernews.data.models.ArticlesList
import retrofit2.Response
import retrofit2.http.GET

interface ArticleService {
    @GET("v1/search_by_date?query=android&tags=story")
    suspend fun getAllArticles() : Response<ArticlesList>
}