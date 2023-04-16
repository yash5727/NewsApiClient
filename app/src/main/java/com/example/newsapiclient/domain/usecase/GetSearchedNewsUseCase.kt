package com.example.newsapiclient.domain.usecase

import com.example.newsapiclient.data.APIResponse
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(
    private val newsRepository: NewsRepository
)  {
    suspend fun execute(searchQuery: String): Resource<APIResponse>{
        return newsRepository.getSearchedNewsHeadlines(searchQuery)
    }
}