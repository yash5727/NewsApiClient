package com.example.newsapiclient.data.repository.datasource

import com.example.newsapiclient.data.APIResponse
import retrofit2.Response

interface NewsRemoteDatasource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>
    suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Response<APIResponse>
}