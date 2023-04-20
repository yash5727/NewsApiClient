package com.example.newsapiclient.data.repository.datasource

import com.example.newsapiclient.data.model.Article

interface NewsLocalDatasource {
    suspend fun saveArticleToDb(article: Article)
}