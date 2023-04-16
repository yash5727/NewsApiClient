package com.example.newsapiclient.domain.usecase

import com.example.newsapiclient.data.Article
import com.example.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(
    private val newsRepository: NewsRepository
)  {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}