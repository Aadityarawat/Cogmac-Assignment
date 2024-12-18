package com.example.cogmacassignment.repository

import androidx.lifecycle.LiveData
import com.example.cogmacassignment.room.database.BookDatabase
import com.example.cogmacassignment.room.entities.Book
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookDatabase: BookDatabase) {

    fun getUser() : LiveData<List<Book>> {
        return bookDatabase.bookDao().getBooks()
    }

    suspend fun insertUser(book: Book){
        bookDatabase.bookDao().insertBook(book)
    }

    suspend fun deleteNote(book: Book){
        bookDatabase.bookDao().deleteBook(book)
    }

    suspend fun updateNote(book: Book){
        bookDatabase.bookDao().updateBook(book.id, book.title, book.author, book.publicationDate, book.genre, book.readStatus)
    }
}