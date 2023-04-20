package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.db.ArticleDao
import com.example.newsapiclient.data.repository.datasource.NewsLocalDatasource
import com.example.newsapiclient.data.repository.datasourceImpl.NewsLocalDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(articleDao: ArticleDao): NewsLocalDatasource{
        return NewsLocalDatasourceImpl(articleDao)
    }
}