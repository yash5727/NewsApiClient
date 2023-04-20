package com.example.newsapiclient.data.repository.datasourceImpl

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.api.NewsApiService
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDatasource
import retrofit2.Response

class NewsRemoteDatasourceImpl(
    private val newsApiService: NewsApiService,
): NewsRemoteDatasource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsApiService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsApiService.getSeachedTopHeadlines(country, searchQuery, page)
    }
}