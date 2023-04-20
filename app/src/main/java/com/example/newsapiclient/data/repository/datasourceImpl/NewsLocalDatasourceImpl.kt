package com.example.newsapiclient.data.repository.datasourceImpl

import com.example.newsapiclient.data.db.ArticleDao
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.repository.datasource.NewsLocalDatasource
import kotlinx.coroutines.flow.Flow

class NewsLocalDatasourceImpl(
    private val articleDao: ArticleDao
): NewsLocalDatasource {
    override suspend fun saveArticleToDb(article: Article) {
        articleDao.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }
}