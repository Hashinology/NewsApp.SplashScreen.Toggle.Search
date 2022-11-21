package com.example.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.api.MyRepository
import com.example.newsapp.data.NewsDataBase
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.util.Resource
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application){

    private val _getNewsFromApi: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val getNewsFromApi: LiveData<Resource<NewsResponse>> = _getNewsFromApi


    private val _searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: LiveData<Resource<NewsResponse>> = _searchNews


    val getNewsFromRoom: LiveData<List<Article>>
    val repo: MyRepository

    init{
        val dao = NewsDataBase.getDatabase(application.applicationContext).getDao()
        repo = MyRepository(dao)
        // Livedata coming from database
        getNewsFromRoom = repo.allNewsLiveData
    }

    suspend fun insertNews(news: Article) {
        repo.insert(news)
    }

    suspend fun deleteArticle(news: Article){
        repo.delete(news)
    }



    suspend fun getNewsFromRetrofit(){
        _getNewsFromApi.postValue(Resource.Loading())
        try {
            var response = repo.getBreakingNews("US",1)

            if (response.isSuccessful){
                _getNewsFromApi.postValue(Resource.Success(response.body()!!))
            }else{
                _getNewsFromApi.postValue(Resource.Error(response.message()))
            }
        }catch (t: Throwable){
            when (t){
                is IOException -> _getNewsFromApi.postValue(Resource.Error("Network Failure"))
                else -> _getNewsFromApi.postValue(Resource.Error(t.message.toString()))
            }
        }
    }

    suspend fun searchNews(searchWord: String){
        _searchNews.postValue(Resource.Loading())
        try {
            var response = repo.searchNews(searchWord,1)

            if (response.isSuccessful){
                _searchNews.postValue(Resource.Success(response.body()!!))
            }else{
                _searchNews.postValue(Resource.Error(response.message()))
            }
        }catch (t: Throwable){
            when (t){
                is IOException -> _searchNews.postValue(Resource.Error("Network Failure"))
                else -> _searchNews.postValue(Resource.Error(t.message.toString()))
            }
        }
    }

}