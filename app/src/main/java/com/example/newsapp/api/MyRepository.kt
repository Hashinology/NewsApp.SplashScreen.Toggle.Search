package com.example.newsapp.api

import androidx.lifecycle.LiveData
import com.example.newsapp.data.NewsDao
import com.example.newsapp.model.Article


class MyRepository(private val dao: NewsDao) {


    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitClient.getInstance().getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitClient.getInstance().searchForNews(searchQuery, pageNumber)

    val allNewsLiveData: LiveData<List<Article>> = dao.getAllNews()

    suspend fun insert(news: Article) {
        dao.insert(news)
    }

    suspend fun delete(news: Article) {
        dao.deleteNews(news)
    }

}