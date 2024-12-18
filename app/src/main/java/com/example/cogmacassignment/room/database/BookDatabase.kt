package com.example.cogmacassignment.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cogmacassignment.room.dao.BookDao
import com.example.cogmacassignment.room.entities.Book

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase(){

    abstract fun bookDao() : BookDao
}