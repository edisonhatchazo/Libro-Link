package com.edison.librolink.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edison.librolink.data.retrofit.BookItem
import com.edison.librolink.data.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<BookItem>>(emptyList())
    val books: StateFlow<List<BookItem>> = _books

    private var currentIndex = 0
    private val pageSize = 10 // Number of books to load per page
    private var isLoading = false

    fun loadMoreBooks(query: String) {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchBooks(query, startIndex = currentIndex)
                response.items?.let { newBooks ->
                    val uniqueBooks = newBooks.filter { newBook ->
                        _books.value.none { it.volumeInfo.title == newBook.volumeInfo.title }
                    }
                    _books.value += uniqueBooks // Append new books
                    currentIndex += pageSize
                }
            } catch (e: Exception) {
                Log.e("BooksViewModel", "Error loading books: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun clearBooks(){
        _books.value = emptyList()
        currentIndex = 0
    }
}