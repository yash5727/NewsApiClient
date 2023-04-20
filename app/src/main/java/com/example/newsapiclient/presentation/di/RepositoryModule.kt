package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.repository.NewsRepositoryImpl
import com.example.newsapiclient.data.repository.datasource.NewsLocalDatasource
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDatasource
import com.example.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDatasource: NewsRemoteDatasource,
        newsLocalDatasource: NewsLocalDatasource
    ):NewsRepository{
        return NewsRepositoryImpl(newsRemoteDatasource,newsLocalDatasource)
    }
}