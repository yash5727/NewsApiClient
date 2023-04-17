package com.example.newsapiclient.api

import com.example.newsapiclient.BuildConfig
import com.example.newsapiclient.data.api.NewsApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    private lateinit var service: NewsApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Test
    fun getTopHeadlines_sentRequest_receiveExpected(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=4eed2a7ad66247b1824f394bfb771e50")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articleList = responseBody!!.articles
            assertThat(articleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articleList = responseBody!!.articles
            assertThat(articleList[0].author).isEqualTo("https://www.facebook.com/bbcnews")
            assertThat(articleList[0].url).isEqualTo("https://www.bbc.com/news/world-africa-65293538")
            assertThat(articleList[0].publishedAt).isEqualTo("2023-04-16T17:38:29Z")

        }
    }

    private fun enqueueMockResponse(
        fileName: String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}