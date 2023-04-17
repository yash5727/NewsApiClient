package com.example.newsapiclient.data.repository.datasourceImpl

import com.example.newsapiclient.data.APIResponse
import com.example.newsapiclient.data.api.NewsApiService
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDatasource
import retrofit2.Response

class NewsRemoteDatasourceImpl(
    private val country: String,
    private val newsApiService: NewsApiService,
    private val page: Int
): NewsRemoteDatasource {
    override suspend fun getTopHeadlines(): Response<APIResponse> {
        return newsApiService.getTopHeadlines(country, page)
    }
}