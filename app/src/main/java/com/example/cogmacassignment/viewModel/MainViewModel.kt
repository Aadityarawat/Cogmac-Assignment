package com.example.cogmacassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cogmacassignment.repository.BookRepository
import com.example.cogmacassignment.room.entities.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel(){

    var bookData : Book? = null

    fun getBooks() : LiveData<List<Book>>{
        return bookRepository.getUser()
    }

    fun insertBook(book: Book){
        viewModelScope.launch {
            bookRepository.insertUser(book)
        }
    }

    fun updateBook(book : Book){
        viewModelScope.launch {
            bookRepository.updateNote(book)
        }
    }

    fun deleteBook(book : Book){
        viewModelScope.launch {
            bookRepository.deleteNote(book)
        }
    }
}