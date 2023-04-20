package com.example.newsapiclient.data.repository.datasourceImpl

import com.example.newsapiclient.data.db.ArticleDao
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.repository.datasource.NewsLocalDatasource

class NewsLocalDatasourceImpl(
    private val articleDao: ArticleDao
): NewsLocalDatasource {
    override suspend fun saveArticleToDb(article: Article) {
        articleDao.insert(article)
    }
}