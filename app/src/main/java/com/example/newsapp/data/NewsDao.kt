package com.example.newsapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: Article)

    @Query("SELECT * FROM NewsTable")
    fun getAllNews(): LiveData<List<Article>>

    @Delete
    suspend fun deleteNews(news: Article)

}