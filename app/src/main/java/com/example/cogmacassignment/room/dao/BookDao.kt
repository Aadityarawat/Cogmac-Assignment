package com.example.cogmacassignment.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.example.cogmacassignment.room.entities.Book

@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(book: Book)

    @Query("update books_table set title = :title, author = :author, publicationDate = :date, genre = :genre, readStatus = :read where id = :id")
    suspend fun updateBook(id : Int?, title : String?, author: String?, date: String?, genre: String?, read: Boolean?)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("Select * from books_table")
    fun getBooks() : LiveData<List<Book>>

}