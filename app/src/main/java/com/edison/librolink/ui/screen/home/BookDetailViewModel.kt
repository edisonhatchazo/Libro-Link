package com.edison.librolink.ui.screen.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edison.librolink.data.FavoriteBook
import com.edison.librolink.data.FavoriteBookRepository
import com.edison.librolink.data.retrofit.BookItem
import com.edison.librolink.data.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val favoriteBookRepository: FavoriteBookRepository
): ViewModel() {
    private val bookId: String = checkNotNull(savedStateHandle[HomeBookDetailDestination.BOOKDETAILIDARG])

    private val _book = MutableStateFlow<BookItem?>(null)
    val book: StateFlow<BookItem?> get() = _book

    private val _uiState = MutableStateFlow(FavoriteBookUiState())
    val uiState: StateFlow<FavoriteBookUiState> get() = _uiState

    init{
        fetchBookDetails()
    }

    private fun fetchBookDetails() {
        viewModelScope.launch {
            try {
                val bookItem = RetrofitInstance.api.getBookById(bookId)
                _book.value = bookItem

                // Check if the book is already in favorites
                val isFavorite = favoriteBookRepository.getFavoriteBook(bookItem.id) != null

                _uiState.value = FavoriteBookUiState(
                    favoriteBookDetails = bookItem.toFavoriteBookDetails(),
                    isFavorite = isFavorite
                )
            } catch (e: Exception) {
                Log.e("BookDetailViewModel", "Error fetching book details: ${e.message}")
            }
        }
    }

    private fun addToFavorites() {
        viewModelScope.launch {
            val currentBook = _book.value
            val currentUiState = _uiState.value
            if (currentBook != null) {
                val favoriteBook = FavoriteBook(
                    id = currentBook.id,
                    title = currentBook.volumeInfo.title,
                    subtitle = currentBook.volumeInfo.subtitle,
                    authors = currentBook.volumeInfo.authors?.joinToString(", "),
                    publisher = currentBook.volumeInfo.publisher,
                    publishedDate = currentBook.volumeInfo.publishedDate,
                    pageCount = currentBook.volumeInfo.pageCount?.toIntOrNull(),
                    description = currentBook.volumeInfo.description,
                    imageUrl = currentBook.volumeInfo.imageLinks?.thumbnail
                )
                favoriteBookRepository.insertFavoriteBook(favoriteBook)
                _uiState.value = currentUiState.copy(isFavorite = true)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentBook = _book.value
            val currentUiState = _uiState.value
            if (currentBook != null) {
                val isFavorite = favoriteBookRepository.getFavoriteBook(currentBook.id) != null
                if (isFavorite) {
                    favoriteBookRepository.deleteFavoriteBook(currentBook.toFavoriteBook())
                    _uiState.value = currentUiState.copy(isFavorite = false)
                } else {
                    addToFavorites()
                }
            }
        }
    }


}

data class FavoriteBookUiState(
    val favoriteBookDetails: FavoriteBookDetails = FavoriteBookDetails(),
    val isFavorite: Boolean = false
)

data class FavoriteBookDetails(
    val id: Int = 0,
    val bookId: String = "",
    val title: String = "",
    val subtitle: String = "",
    val authors: String = "",
    val publisher: String = "",
    val publishedDate: String = "",
    val pageCount: Int = 0,
    val description: String = "",
    val imageUrl: String = ""
)

fun BookItem.toFavoriteBook(): FavoriteBook{
    return FavoriteBook(
        id = this.id,
        title = this.volumeInfo.title,
        subtitle = this.volumeInfo.subtitle.orEmpty(),
        authors = this.volumeInfo.authors?.joinToString(", ").orEmpty(),
        publisher = this.volumeInfo.publisher.orEmpty(),
        publishedDate = this.volumeInfo.publishedDate.orEmpty(),
        pageCount = this.volumeInfo.pageCount?.toIntOrNull() ?: 0,
        description = this.volumeInfo.description.orEmpty(),
        imageUrl = this.volumeInfo.imageLinks?.thumbnail.orEmpty()
    )
}

fun BookItem.toFavoriteBookDetails(): FavoriteBookDetails {
    return FavoriteBookDetails(
        bookId = this.id,
        title = this.volumeInfo.title,
        subtitle = this.volumeInfo.subtitle.orEmpty(),
        authors = this.volumeInfo.authors?.joinToString(", ").orEmpty(),
        publisher = this.volumeInfo.publisher.orEmpty(),
        publishedDate = this.volumeInfo.publishedDate.orEmpty(),
        pageCount = this.volumeInfo.pageCount?.toIntOrNull() ?: 0,
        description = this.volumeInfo.description.orEmpty(),
        imageUrl = this.volumeInfo.imageLinks?.thumbnail.orEmpty()
    )
}

