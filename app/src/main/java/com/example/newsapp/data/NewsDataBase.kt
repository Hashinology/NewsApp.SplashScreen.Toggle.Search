package com.example.newsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.model.Article


@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class NewsDataBase: RoomDatabase() {

    abstract fun getDao(): NewsDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDataBase? = null
        fun getDatabase(context: Context): NewsDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(NewsDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = buildRoom(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }

} private fun buildRoom(context: Context) =
    Room.databaseBuilder(
        context.applicationContext,
        NewsDataBase::class.java,
        "News_database"
    ).build()
