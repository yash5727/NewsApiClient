package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.api.NewsApiService
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDatasource
import com.example.newsapiclient.data.repository.datasourceImpl.NewsRemoteDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideNewsRemoteDatasource(newsApiService: NewsApiService): NewsRemoteDatasource{
        return NewsRemoteDatasourceImpl(newsApiService)
    }
}