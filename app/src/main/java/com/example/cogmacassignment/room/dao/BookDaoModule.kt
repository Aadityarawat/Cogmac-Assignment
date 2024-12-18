package com.example.cogmacassignment.room.dao

import android.content.Context
import androidx.room.Room
import com.example.cogmacassignment.room.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class BookDaoModule {
    @Singleton
    @Provides
    fun providesUserDatabase(@ApplicationContext context : Context) : BookDatabase {
        return Room.databaseBuilder(context, BookDatabase::class.java, "book_database").build()
    }
}