package com.example.newsapiclient.data.repository.datasource

import com.example.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDatasource {
    suspend fun saveArticleToDb(article: Article)
    fun getSavedArticles(): Flow<List<Article>>
}