package com.ferjuarez.readerhackernews.di

import android.content.Context
import com.ferjuarez.hackernews.networking.ArticlesApiDataSource
import com.ferjuarez.readerhackernews.networking.ArticleService
import com.ferjuarez.readerhackernews.persistance.AppDatabase
import com.ferjuarez.readerhackernews.persistance.ArticleDao
import com.ferjuarez.readerhackernews.repository.ArticlesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://hn.algolia.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideArticleService(retrofit: Retrofit): ArticleService = retrofit.create(ArticleService::class.java)

    @Singleton
    @Provides
    fun provideArticleRemoteDataSource(articleService: ArticleService) = ArticlesApiDataSource(articleService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideArticleDao(db: AppDatabase) = db.articlesDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ArticlesApiDataSource,
                          localDataSource: ArticleDao) =
        ArticlesRepository(remoteDataSource, localDataSource)
}