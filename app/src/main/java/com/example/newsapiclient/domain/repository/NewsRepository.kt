package com.example.newsapiclient.domain.repository

import com.example.newsapiclient.data.APIResponse
import com.example.newsapiclient.data.Article
import com.example.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(): Resource<APIResponse>
    suspend fun getSearchedNewsHeadlines(searchQuery: String): Resource<APIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}