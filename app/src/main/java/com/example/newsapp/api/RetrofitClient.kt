package com.example.newsapp.api

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import com.example.newsapp.util.Constants.Companion.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitClient {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>


    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>


    companion object {
        var retrofitClient: RetrofitClient? = null

        fun getInstance() : RetrofitClient {
            if (retrofitClient == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitClient = retrofit.create(RetrofitClient::class.java)
            }
            return retrofitClient!!
        }
    }
}