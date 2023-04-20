package com.example.newsapiclient.data.repository

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.repository.datasource.NewsLocalDatasource
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDatasource
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDatasource: NewsRemoteDatasource,
    private val newsLocalDatasource: NewsLocalDatasource
): NewsRepository {

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDatasource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(
            newsRemoteDatasource.getSearchedNews(country, searchQuery, page)
        )
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDatasource.saveArticleToDb(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDatasource.deleteArticle(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDatasource.getSavedArticles()
    }
}